package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.model.Account;


public class AccountSqlDAO implements AccountDAO{

	
	private JdbcTemplate jdbcTemplate;

    public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


@Override
public Account getBalance(int userId) {
	Account account = new Account();
	
	String sql  = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
	SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
	
	while (results.next()){
	account = mapRowToAccount(results);
	}

	return account;
}

@Override
public BigDecimal withdraw(int fromUserId, BigDecimal amount) {
	String sqlGetStartingBalanceFromSender = "SELECT balance FROM accounts WHERE user_id = ?;";
	
	SqlRowSet balance = jdbcTemplate.queryForRowSet(sqlGetStartingBalanceFromSender, fromUserId);
	
	BigDecimal newBalance = new BigDecimal(0);
	
	while(balance.next()) {
		String line = balance.getString("balance");
		BigDecimal currBalance = new BigDecimal(line);
		newBalance = currBalance.subtract(amount);
	}
	
	String sqlUpdateBalance = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
	
	jdbcTemplate.update(sqlUpdateBalance, fromUserId, newBalance);
	
	
	return newBalance;
	
}

@Override
public BigDecimal add(int toUserId, BigDecimal amount) {
	String sqlGetStartingBalanceFromReceiver = "SELECT balance FROM accounts WHERE user_id = ?;";
	
	SqlRowSet balance = jdbcTemplate.queryForRowSet(sqlGetStartingBalanceFromReceiver, toUserId);
	
	BigDecimal newBalance = new BigDecimal(0);
	
	while(balance.next()) {
		String line = balance.getString("balance");
		BigDecimal currBalance = new BigDecimal(line);
		newBalance = currBalance.add(amount);
	}
	
	String sqlUpdateBalance = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
	
	jdbcTemplate.update(sqlUpdateBalance, toUserId, newBalance);
	
	return newBalance;
	
	
}

private Account mapRowToAccount(SqlRowSet results) {
	Account account = new Account();
	account.setId(results.getInt("account_id"));
	account.setUserId(results.getInt("user_id"));
	account.setBalance(results.getBigDecimal("balance"));
	return account;
	
}
}