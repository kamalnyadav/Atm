package com.zinkworks.springboot.atm.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zinkworks.springboot.atm.bo.helper.AtmHelper;
import com.zinkworks.springboot.atm.dao.AccountDAO;
import com.zinkworks.springboot.atm.dao.AtmDAO;
import com.zinkworks.springboot.atm.entity.Atms;
import com.zinkworks.springboot.atm.entity.BalanceCheck;
import com.zinkworks.springboot.atm.entity.Users;
import com.zinkworks.springboot.atm.rest.InvalidPinException;
import com.zinkworks.springboot.atm.validation.Validation;

@Service
public class AtmBOImpl implements AtmBO {

	@Autowired
	private AtmDAO atmDAOAtms;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	AtmHelper atmHelper;

	@Autowired
	private Validation val;

	private Atms notePairs;

	private Atms noteCounterPairs;

	private Users user;

	private int balance;
	List<Users> accountList;

	@Override
	@Transactional
	public BalanceCheck getBalance(String account, int pin) {
		long balance = 0;
		long limit = 0;
		accountList = accountDAO.getBalance(account, pin);
		if (!accountList.isEmpty()) {
			balance = accountList.get(0).getBalance();
			if (accountList.get(0).getOverdraft() != 0)
				limit = accountList.get(0).getBalance() - accountList.get(0).getOverdraft();
			else
				limit = accountList.get(0).getBalance();
			// return the results
		} else {
			throw new InvalidPinException("Pin entered:" + pin + " is invalid for account: " + account);
		}
		return new BalanceCheck(balance, limit);
	}

	@Override
	@Transactional
	public Atms withdraw(int atmId, String account, int pin, int amount) {
		List<Atms> currentDenominations = atmDAOAtms.getAtm(atmId);
		user = accountDAO.getUser(account, pin);
		Map<Integer, Integer> denominations = atmHelper.getDenominationPairs(currentDenominations.get(0));
		int[] notes = new int[] { 50, 20, 10, 5 };
		int note = 0;
		int[] noteCounter = new int[notes.length];
		val.validateAmount(denominations, amount);
		val.validateAmount(account, pin, amount);
		balance = user.getBalance() - amount;
		for (int i = 0; i < notes.length; i++) {
			if (amount >= notes[i] && denominations.get(notes[i]) != 0
					) {
				note = amount / notes[i];
				if (note <= denominations.get(notes[i])) {
					noteCounter[i] = amount / notes[i];
					amount = amount % notes[i];
					denominations.put(notes[i], denominations.get(notes[i]) - noteCounter[i]);
				} else {
					noteCounter[i] = denominations.get(notes[i]);
					denominations.put(notes[i], 0);
					amount = amount - (noteCounter[i] * notes[i]);
				}

			}

		}
		user.setBalance(balance);
		notePairs = atmHelper.getNotePairs(denominations);
		noteCounterPairs = atmHelper.getNotePairs(noteCounter);
		noteCounterPairs.setAmountDispersed(atmHelper.calculateTotalAmt(noteCounter));
		notePairs.setId(atmId);
		atmDAOAtms.save(notePairs);
		accountDAO.save(user);
		return noteCounterPairs;
	}

	@Override
	@Transactional
	public Atms saveAtm(Atms theAtm) {
		atmDAOAtms.save(theAtm);
		return theAtm;
	}

	@Override
	@Transactional
	public Users saveUser(Users theUser) {
		accountDAO.save(theUser);
		return theUser;
	}
}
