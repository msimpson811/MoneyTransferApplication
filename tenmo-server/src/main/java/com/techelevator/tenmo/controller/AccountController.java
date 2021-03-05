package com.techelevator.tenmo.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.exceptions.InsufficientBalanceException;
import com.techelevator.tenmo.model.Account;

@RestController
//@PreAuthorize("isAuthenticated()")
public class AccountController {

	private AccountDAO accountDao;

	public AccountController(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	@RequestMapping(path = "/user/{userId}/account", method = RequestMethod.GET)
	public Account getBalance(@PathVariable int userId) {
		return accountDao.getBalance(userId);
	}

	@RequestMapping(path = "/withdraw", method = RequestMethod.PUT)
	public BigDecimal withdraw(@RequestParam int fromUserId, @RequestParam BigDecimal amount,@Valid @RequestBody Account account) throws InsufficientBalanceException {
		return accountDao.withdraw(fromUserId, amount);

	}

	@RequestMapping(path = "/receive", method = RequestMethod.PUT)
	public BigDecimal add(@RequestParam int toUserId, @RequestParam BigDecimal amount,@Valid @RequestBody Account account) {
		return accountDao.add(toUserId, amount);
	}
}
