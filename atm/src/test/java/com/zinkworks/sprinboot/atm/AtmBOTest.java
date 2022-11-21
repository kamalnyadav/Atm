package com.zinkworks.sprinboot.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.zinkworks.springboot.atm.AtmApplication;
import com.zinkworks.springboot.atm.bo.AtmBO;
import com.zinkworks.springboot.atm.bo.helper.AtmHelper;
import com.zinkworks.springboot.atm.dao.AccountDAO;
import com.zinkworks.springboot.atm.dao.AtmDAO;
import com.zinkworks.springboot.atm.entity.Atms;
import com.zinkworks.springboot.atm.entity.BalanceCheck;
import com.zinkworks.springboot.atm.entity.Users;
import com.zinkworks.springboot.atm.rest.CommonException;
import com.zinkworks.springboot.atm.rest.InvalidPinException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtmApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class AtmBOTest {

	@Autowired
	private AtmBO atmBO;

	@MockBean
	private AtmDAO atmDAO;

	@MockBean
	private AccountDAO accountDAO;
	
	@Autowired
	private AtmHelper atmHelper;

	private Users user;

	private Atms atm;

	private Users expectedUser;
	
	private Users actualUser;
	
	private Atms actualAtm;
	
	private Atms expectedAtm;
	
	private List<Users> userList;
	
	private List<Atms> atmList;

	public Atms getAtms() {
		atm = new Atms();
		atm.setFifty(10);
		atm.setTen(30);
		atm.setTwenty(30);
		atm.setFive(20);
		atm.setId(1);
		return atm;
	}
	
	public Atms getAtmsLess() {
		atm = new Atms();
		atm.setFifty(2);
		atm.setTen(30);
		atm.setTwenty(30);
		atm.setFive(20);
		atm.setId(1);
		return atm;
	}
	
	public Users getUsers() {
		user = new Users();
		user.setAccountNumber("12345678");
		user.setPin(1234);
		user.setBalance(800);
		user.setOverdraft(200);
		return user;
	}

	public List<Atms> getListAtms() {
		atm = new Atms();
		atm.setFifty(10);
		atm.setTen(30);
		atm.setTwenty(30);
		atm.setFive(20);
		atm.setId(1);
		atmList = new ArrayList<Atms>();
		atmList.add(atm);
		return atmList;
	}
	
	public List<Atms> getListAtmsLess() {
		atm = new Atms();
		atm.setFifty(2);
		atm.setTen(30);
		atm.setTwenty(30);
		atm.setFive(20);
		atm.setId(1);
		atmList = new ArrayList<Atms>();
		atmList.add(atm);
		return atmList;
	}
	
	public List<Users> getListUsers() {
		user = new Users();
		user.setAccountNumber("12345678");
		user.setPin(1234);
		user.setBalance(800);
		user.setOverdraft(200);
		userList = new ArrayList<>();
		userList.add(user);
		return userList;
	}


	@Test
	public void getBalanceHappyPath() {
		Mockito.when(accountDAO.getBalance("12345678", 1234)).thenReturn(getListUsers());
		BalanceCheck actualBalance = atmBO.getBalance("12345678", 1234);
		BalanceCheck expectedBalance = new BalanceCheck(800, 600);
		assertEquals(expectedBalance.getBalance(), actualBalance.getBalance());
		assertEquals(expectedBalance.getMaximumWithdrawal(), actualBalance.getMaximumWithdrawal());
	}

	@Test
	public void getBalanceError() {
		String actualMessage = "";
		String expectedMessage = "Pin entered:1234 is invalid for account: 12345678";
		Mockito.when(accountDAO.getBalance("12345678", 1234)).thenReturn(new ArrayList());
		try {
			atmBO.getBalance("12345678", 1234);
		} catch (InvalidPinException exc) {
			actualMessage = exc.getMessage();
		}

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void saveUserHappyPath() {
		Mockito.doAnswer((i) -> {return "Save Users Successfully";}).when(accountDAO).save(getUsers());
		actualUser = atmBO.saveUser(getUsers());
		expectedUser = getUsers();
		assertEquals(expectedUser.getAccountNumber(), actualUser.getAccountNumber());
		assertEquals(expectedUser.getBalance(), actualUser.getBalance());
		assertEquals(expectedUser.getOverdraft(), actualUser.getOverdraft());
		assertEquals(expectedUser.getPin(), actualUser.getPin());
	}
	
	@Test
	public void saveAtmHappyPath() {
		Mockito.doAnswer((i) -> {return "Save Atm Successfully";}).when(atmDAO).save(getAtms());
		actualAtm = atmBO.saveAtm(getAtms());
		expectedAtm = getAtms();
		assertEquals(expectedAtm.getFifty(), actualAtm.getFifty());
		assertEquals(expectedAtm.getTwenty(), actualAtm.getTwenty());
		assertEquals(expectedAtm.getFive(), actualAtm.getFive());
		assertEquals(expectedAtm.getTen(), actualAtm.getTen());
	}
	
	@Test
	public void withdrawHappyPath() {
		atm = getAtms();
		user = getUsers();
		Mockito.doAnswer((i) -> {return "Save Atm Successfully";}).when(atmDAO).save(getAtms());
		Mockito.doAnswer((i) -> {return "Save Users Successfully";}).when(accountDAO).save(getUsers());
		Mockito.when(accountDAO.getBalance(user.getAccountNumber(), user.getPin())).thenReturn(getListUsers());
		Mockito.when(atmDAO.getAtm(atm.getId())).thenReturn(getListAtms());
		Mockito.when(accountDAO.getUser(user.getAccountNumber(), user.getPin())).thenReturn(getUsers());
		actualAtm = atmBO.withdraw(atm.getId(), user.getAccountNumber(), user.getPin(), 400);
		expectedAtm = getAtms();
		Map<Integer, Integer> denominations = atmHelper.getDenominationPairs(actualAtm);
		int actualAtmBalance = atmHelper.calculateTotalAmt(denominations);
		int expectedAtmBalance = 400;
		assertEquals(expectedAtmBalance, actualAtmBalance);

	}
	
	@Test
	public void withdrawLowBalance() {
		atm = getAtms();
		user = getUsers();
		Mockito.doAnswer((i) -> {return "Save Atm Successfully";}).when(atmDAO).save(getAtms());
		Mockito.doAnswer((i) -> {return "Save Users Successfully";}).when(accountDAO).save(getUsers());
		Mockito.when(accountDAO.getBalance(user.getAccountNumber(), user.getPin())).thenReturn(getListUsers());
		Mockito.when(atmDAO.getAtm(atm.getId())).thenReturn(getListAtms());
		Mockito.when(accountDAO.getUser(user.getAccountNumber(), user.getPin())).thenReturn(getUsers());
		String actualMessage = "Please enter an amount less than: 1500";
		String expectedMessage = "";
		try {
		atmBO.withdraw(atm.getId(), user.getAccountNumber(), user.getPin(), 10000);
		}
		catch(CommonException exc) {
			expectedMessage = exc.getMessage();
		}

		assertEquals(expectedMessage, actualMessage);

	}
	
	@Test
	public void withdrawLowAccountBalance() {
		atm = getAtms();
		user = getUsers();
		Mockito.doAnswer((i) -> {return "Save Atm Successfully";}).when(atmDAO).save(getAtmsLess());
		Mockito.doAnswer((i) -> {return "Save Users Successfully";}).when(accountDAO).save(getUsers());
		Mockito.when(accountDAO.getBalance(user.getAccountNumber(), user.getPin())).thenReturn(getListUsers());
		Mockito.when(atmDAO.getAtm(atm.getId())).thenReturn(getListAtmsLess());
		Mockito.when(accountDAO.getUser(user.getAccountNumber(), user.getPin())).thenReturn(getUsers());
		Mockito.when(accountDAO.getWithdrawalLimt(user.getAccountNumber(), user.getPin())).thenReturn(600);
		String actualMessage = "Please enter an amount less than: 600 as you have low balance";
		//String actualMessage = "Please enter an amount less than: 1500";
		String expectedMessage = "";
		try {
		atmBO.withdraw(atm.getId(), user.getAccountNumber(), user.getPin(), 900);
		}
		catch(CommonException exc) {
			expectedMessage = exc.getMessage();
		}

		assertEquals(expectedMessage, actualMessage);

	}
	
	@Test
	public void withdrawLessFiftyNotes() {
		actualAtm = getAtmsLess();
		user = getUsers();
		Mockito.doAnswer((i) -> {return "Save Atm Successfully";}).when(atmDAO).save(getAtms());
		Mockito.doAnswer((i) -> {return "Save Users Successfully";}).when(accountDAO).save(getUsers());
		Mockito.when(accountDAO.getBalance(user.getAccountNumber(), user.getPin())).thenReturn(getListUsers());
		Mockito.when(atmDAO.getAtm(atm.getId())).thenReturn(getListAtms());
		Mockito.when(accountDAO.getUser(user.getAccountNumber(), user.getPin())).thenReturn(getUsers());
		Mockito.when(accountDAO.getWithdrawalLimt(user.getAccountNumber(), user.getPin())).thenReturn(1100);
		
		Map<Integer, Integer> denominations = atmHelper.getDenominationPairs(actualAtm);
		System.out.println("denominations are: " + denominations);
		int actualAtmBalance = atmHelper.calculateTotalAmt(denominations);
		int expectedAtmBalance = 1100;
		assertEquals(expectedAtmBalance, actualAtmBalance);
	}
}
