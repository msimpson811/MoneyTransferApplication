package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	Transfer getTransferById(int id);
	
	List<Transfer> getAllTransfersByUserId(int id);
	
	Transfer transfer(Transfer transfer);
	
	String getTransferStatus(int id);
	
	String getTransferType(int id);
}