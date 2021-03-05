package com.techelevator.tenmo.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;
@RestController
//@PreAuthorize("isAuthenticated()")
public class AccountController {
	
	private AccountDAO accountDao;
	private AuthenticationController authentication;
	public AccountController(AccountDAO accountDao, AuthenticationController authentication) {
		this.accountDao = accountDao;
		this.authentication = authentication;
	}

	
	@RequestMapping(path = "/user/{userId}/account", method = RequestMethod.GET)
	public Account getBalance(@PathVariable int userId) {
		return accountDao.getBalance(userId);
	}
	
	@RequestMapping(path = "/account", method = RequestMethod.POST)
	public BigDecimal withdraw(@RequestParam int fromUserId, @RequestParam BigDecimal amount) {
		return accountDao.withdraw(fromUserId, amount);
		
	}
	
	@RequestMapping(path = "/account", method = RequestMethod.POST)
	public BigDecimal add (@RequestParam int toUserId, @RequestParam BigDecimal amount) {
		return accountDao.withdraw(toUserId, amount);
	}
}