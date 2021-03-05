package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import com.techelevator.tenmo.exceptions.InsufficientBalanceException;
import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

	Account getBalance(int userId);
	
	BigDecimal withdraw(int fromUserId, BigDecimal amount) throws InsufficientBalanceException;
	
	BigDecimal add(int fromUserId, BigDecimal amount);
}
