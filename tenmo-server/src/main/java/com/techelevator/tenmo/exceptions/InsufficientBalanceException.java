package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.NOT_MODIFIED, reason = "Insufficient Balance.")
public class InsufficientBalanceException extends Exception {

	//added default to get rid of warning
	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException() {
		super("Insufficient Balance");
	}
}
