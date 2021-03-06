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

	
	@Override
	public Transfer transfer(Transfer transfer) {
		String sqlSaveTransferRecord = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "VALUES (2, 2, ?, ?, ?);";
		
		Transfer newTransfer = jdbcTemplate.queryForObject(sqlSaveTransferRecord, Transfer.class, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		
		return newTransfer;
	}

	// Not sure if we will actually need this method
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

	// Not sure if we will actually need this method
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
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }
    
}

