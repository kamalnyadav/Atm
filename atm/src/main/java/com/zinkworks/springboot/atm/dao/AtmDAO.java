package com.zinkworks.springboot.atm.dao;

import java.util.List;

import com.zinkworks.springboot.atm.entity.Atms;

public interface AtmDAO {
	public List<Atms> getAtm(int id);
	
	public void save(Atms theAtm);
}
