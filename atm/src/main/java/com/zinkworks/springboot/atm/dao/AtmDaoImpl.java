package com.zinkworks.springboot.atm.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zinkworks.springboot.atm.entity.Atms;

@Repository
public class AtmDaoImpl implements AtmDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Atms> getAtm(int id) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Atms> theQuery =
				currentSession.createQuery("from Atms where id=:id", Atms.class);
		theQuery.setParameter("id", id);
		// execute query and get result list
		List<Atms> atms = theQuery.getResultList();
		currentSession.flush();
		currentSession.clear();
		return atms;
	}
	
	@Override
	public void save(Atms theAtm) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save employee
		currentSession.saveOrUpdate(theAtm);
	}


}
