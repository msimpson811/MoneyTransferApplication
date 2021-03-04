package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	BigDecimal withdraw(int fromUserId, BigDecimal amount);
	
	BigDecimal add(int toUserId, BigDecimal amount);
	
	Transfer getTransferById(int id);
	
	List<Transfer> getAllTransfersByUserId(int id);
	
	void transfer(int fromUserId, int toUserId, BigDecimal amount);
	
	String getTransferStatus(int id);
	
	String getTransferType(int id);
}