package com.zinkworks.springboot.atm.validation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.zinkworks.springboot.atm.bo.helper.AtmHelper;
import com.zinkworks.springboot.atm.dao.AccountDAO;
import com.zinkworks.springboot.atm.dao.AtmDAO;
import com.zinkworks.springboot.atm.entity.Users;
import com.zinkworks.springboot.atm.rest.CommonException;

@Component
public class ValidationImpl implements Validation {

	@Autowired
	@Qualifier("atmDaoImpl")
	private AtmDAO atmDAOAtms;

	@Autowired
	@Qualifier("accountDAOImpl")
	private AccountDAO accountDAO;

	@Autowired
	private AtmHelper atmHelper;
	
	Users user;

	@Override
	public boolean validatePin(String account, int pin) {
		List<Users> user = accountDAO.getBalance(account, pin);
		return user.isEmpty();
	}


	public void validateAmount(Map<Integer, Integer> currentDenominations, int amount) {
		int currentBalance = atmHelper.calculateTotalAmt(currentDenominations);
		if (amount % 5 != 0 || amount == 0)
			throw new CommonException("Entered amount: " + amount+" is invalid. Enter amount in multiples of 5");
		if(amount > currentBalance)
			throw new CommonException("Please enter an amount less than: " + currentBalance);
	}
	
	public void validateAmount(String account, int pin, int amount) {
		int maxWithdrawalAmt = accountDAO.getWithdrawalLimt(account, pin);
		user = accountDAO.getUser(account, pin);
		if(amount > maxWithdrawalAmt && maxWithdrawalAmt > 0)
			throw new CommonException("Please enter an amount less than: " + maxWithdrawalAmt + " as you have low balance");
		else if(maxWithdrawalAmt<0) {
			throw new CommonException("Please try after depositing cash as you maintain overdraft of: " + user.getOverdraft() + " and you have balance of only: " + user.getBalance());
		}
	}

}
