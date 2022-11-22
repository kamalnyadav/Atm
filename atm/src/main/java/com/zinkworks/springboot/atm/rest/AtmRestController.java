package com.zinkworks.springboot.atm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zinkworks.springboot.atm.bo.AtmBO;
import com.zinkworks.springboot.atm.entity.Atms;
import com.zinkworks.springboot.atm.entity.BalanceCheck;
import com.zinkworks.springboot.atm.entity.Users;
import com.zinkworks.springboot.atm.validation.Validation;

@RestController
@RequestMapping("/api")
public class AtmRestController {

	@Autowired
	private AtmBO atmBO;
	
	@Autowired
	private Validation val;

	/*
	 * @GetMapping("/users") public List<Users> findAll() { return
	 * atmDAOUsers.findAll(); }
	 */
	/*
	 * @GetMapping("/atms") public List<Atms> findAllAtm() { return
	 * atmDAOAtms.findAll(); }
	 */

	@GetMapping("/balance")
	public BalanceCheck getBalance(@RequestParam String account, @RequestParam String pin) {
		int parsedPin = 0;
		try {
			parsedPin = Integer.parseInt(pin);
		} catch (NumberFormatException exc) {
			throw new InvalidPinException("Pin entered:" + pin + " should be of type Integer");
		}
		if(val.validatePin(account, parsedPin)) {
			throw new InvalidPinException("Pin entered:" + pin + " is invalid for account: " + account);
		}
		return atmBO.getBalance(account, parsedPin);
	}
	
	@PostMapping("/withdraw/{atmId}")
	public Atms postWithdraw(@RequestBody Users user, @PathVariable int atmId) {
		String account = user.getAccountNumber();
		int pin = user.getPin();
		int amount = user.getAmount();
		if(val.validatePin(account, pin)) {
			throw new InvalidPinException("Pin entered:" + pin + " is invalid for account: " + account);
		}
		System.out.println("Amount is: " + amount);
		return atmBO.withdraw(atmId, account, pin, amount);
	}
	
	@PostMapping("/reset/{atmId}")
	public Atms postRestAtm(@RequestBody Atms theAtm, @PathVariable int atmId) {
		theAtm.setId(atmId);
		return atmBO.saveAtm(theAtm);
	}
}
