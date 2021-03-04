package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@RestController
//@PreAuthorize("isAuthenticated()")
public class TransferController {

	private TransferDAO transferDao;
	private AuthenticationController authentication;
	
	public TransferController (TransferDAO transferDao, AuthenticationController authentication) {
		this.transferDao = transferDao;
		this.authentication = authentication;
	}
	
	@RequestMapping(path = "/user/{userId}/transfer", method = RequestMethod.GET)
	public List<Transfer> list(@PathVariable int userId) {
		
		return transferDao.getAllTransfersByUserId(userId);
	}
	
	@RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
	public Transfer getTransfer(@PathVariable int transferId) {
		
		return transferDao.getTransferById(transferId);
	}
	
	@RequestMapping(path = "/transfer", method = RequestMethod.POST)
	public void saveTransfer(@RequestParam int fromUserId, @RequestParam int toUserId, @RequestParam BigDecimal amount) {
		transferDao.transfer(fromUserId, toUserId, amount);
	}
}
