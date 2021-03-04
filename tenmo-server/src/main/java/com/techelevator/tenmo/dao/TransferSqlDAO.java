package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO {
	
	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
	@Override
	public Transfer getTransferById(int id) {
		String sqlGetAllTransfer = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount "
				+ "FROM transfers WHERE transfer_id = ?;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllTransfer, id);
		
		Transfer transfer = new Transfer();
		
		while (results.next()) {
			transfer = mapRowToTransfer(results);
		}
		return transfer;
	}

	@Override
	public List<Transfer> getAllTransfersByUserId(int id) {
		String sqlGetAllTransfers = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount "
				+ "FROM transfers;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllTransfers);
		
		List<Transfer> transfers = new ArrayList<>();
		
		while (results.next()) {
			Transfer transfer = mapRowToTransfer(results);
			transfers.add(transfer);
		}
		return transfers;
	}

	// cut this one
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

	// cut this one
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
	
    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setId(results.getInt("transfer_id"));
        transfer.setTypeId(results.getInt("transfer_type_id"));
        transfer.setStatusId(results.getInt("transfer_status_id"));
        transfer.setFrom(results.getInt("account_from"));
        transfer.setTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }
    
}
