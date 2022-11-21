package com.zinkworks.springboot.atm.bo;

import com.zinkworks.springboot.atm.entity.Atms;
import com.zinkworks.springboot.atm.entity.BalanceCheck;
import com.zinkworks.springboot.atm.entity.Users;

public interface AtmBO {

	public BalanceCheck getBalance(String account, int pin);
	
	public Atms withdraw(int atmId, String account, int pin, int amount);
	
	public Atms saveAtm(Atms theAtm);
	
	public Users saveUser(Users theUser);
}
