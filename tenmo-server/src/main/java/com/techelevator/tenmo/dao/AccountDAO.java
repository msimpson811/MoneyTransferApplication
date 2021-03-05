package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

	Account getBalance(int userId);
	
	BigDecimal withdraw(int fromUserId, BigDecimal amount);
	
	BigDecimal add(int fromUserId, BigDecimal amount);
}
