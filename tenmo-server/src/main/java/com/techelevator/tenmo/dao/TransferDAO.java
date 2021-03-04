package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDAO {

//	void logTransfer(int fromUserId, int toUserId, BigDecimal amount);
	
	BigDecimal withdraw(int fromUserId, BigDecimal amount);
	
	BigDecimal add(int toUserId, BigDecimal amount);
	
	Transfer getTransferById(int Id);
	
	List<Transfer> getAllTransfersByUserId(int id);
	
	void transfer(int fromUserId, int toUserId, BigDecimal amount);
	
	String getTransferStatus(int id);
	
	String getTransferType(int id);
}
