package com.zinkworks.springboot.atm.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zinkworks.springboot.atm.bo.AtmBO;
import com.zinkworks.springboot.atm.entity.Atms;
import com.zinkworks.springboot.atm.entity.Users;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private EntityManager entityManager;
	
	private Users user;
	
	@Override
	@Transactional
	public Users getUser(String account, int pin) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Users> theQuery =
				currentSession.createQuery("from Users where account_number=:accountNumber and pin=:pin", Users.class);
		theQuery.setParameter("accountNumber", account);
		theQuery.setParameter("pin", pin);
		// execute query and get result list
		List<Users> users = theQuery.getResultList();
		// return the results		
		return users.get(0);
	}
	
	@Override
	@Transactional
	public List<Users> getBalance(String account, int pin) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Users> theQuery =
				currentSession.createQuery("from Users where account_number=:accountNumber and pin=:pin", Users.class);
		theQuery.setParameter("accountNumber", account);
		theQuery.setParameter("pin", pin);

		// execute query and get result list
		List<Users> atms = theQuery.getResultList();
		return atms;
	}
	
	@Override
	@Transactional
	public List<Users> validateUser(String account, int pin) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Users> theQuery =
				currentSession.createQuery("from Users where account_number=:accountNumber and pin=:pin", Users.class);
		theQuery.setParameter("accountNumber", account);
		theQuery.setParameter("pin", pin);
		// execute query and get result list
		List<Users> users = theQuery.getResultList();
		// return the results		
		return users;
	}
	
	@Override
	@Transactional
	public int getWithdrawalLimt(String account, int pin) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Users> theQuery =
				currentSession.createQuery("from Users where account_number=:accountNumber and pin=:pin", Users.class);
		theQuery.setParameter("accountNumber", account);
		theQuery.setParameter("pin", pin);

		// execute query and get result list
		List<Users> atms = theQuery.getResultList();
		if(!atms.isEmpty())
			user = atms.get(0);
		return user.getBalance()-user.getOverdraft();
	}
	
	@Override
	public void save(Users theUser) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save employee
		currentSession.saveOrUpdate(theUser);
	}
	

}
