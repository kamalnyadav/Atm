package com.zinkworks.springboot.atm.validation;

import java.util.Map;

public interface Validation {

	public boolean validatePin(String account, int pin);
	
	public void validateAmount(Map<Integer, Integer> currentDenominations, int amount);
	
	public void validateAmount(String account, int pin, int amount);
}
