package com.zinkworks.springboot.atm.dao;

import java.util.List;

import com.zinkworks.springboot.atm.entity.Users;

public interface AccountDAO {
	public Users getUser(String acoount, int pin);
	public List<Users> getBalance(String account, int pin);
	public List<Users> validateUser(String account, int pin);
	public int getWithdrawalLimt(String account, int pin);
	public void save(Users user);
}
