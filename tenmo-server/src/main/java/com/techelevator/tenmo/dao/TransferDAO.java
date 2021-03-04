package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDAO {

	void logTransfer();
	
	Transfer getTransferById(int Id);
	
	List<Transfer> getAllTransfersByUserId(int id);
	
	void transfer(int fromUserId, int toUserId, BigDecimal amount);
	
	String getTransferStatus(int id);
	
	String getTransferType(int id);
}
