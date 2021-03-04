package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class TransferSqlDAO implements TransferDAO {
	
	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
//	@Override
//	public void logTransfer(int fromUserId, int toUserId, BigDecimal amount) {
//
//		
//	}

	@Override
	public Transfer getTransferById(int Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> getAllTransfersByUserId(int id) {
		// TODO Auto-generated method stub
		return null;
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
	
	@Override
	public void transfer(int fromUserId, int toUserId, BigDecimal amount) {
		withdraw(fromUserId, amount);
		add(toUserId, amount);
		
		String sqlSaveTransferRecord = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (2, 2, ?, ?, ?);";
		
		jdbcTemplate.update(sqlSaveTransferRecord, fromUserId, toUserId, amount);
	}

	@Override
	public String getTransferStatus(int id) {
		String sqlGetStatus = "SELECT transfer_status_desc FROM transfer_statuses WHERE transfer_status_id = ?;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetStatus, id);
		
		String status = "";
		while(results.next()) {
			status = results.getString("transfer_status_id");
		}
		return status;
	}

	@Override
	public String getTransferType(int id) {
		String sqlGetType = "SELECT transfer_type_desc FROM transfer_types WHERE transfer_type_id = ?;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetType, id);
		
		String type = "";
		while(results.next()) {
			type = results.getString("transfer_status_id");
		}
		return type;
	}





}
