package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import com.techelevator.tenmo.models.Account;

public class AccountService {

	private static String TOKEN = "";
	private final String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	public AccountService(String url) {
		this.BASE_URL = url;
	}
	
	public Account getBalance(int userId) {
		
		Account account = new Account();
		try {
		account = restTemplate.exchange(BASE_URL + "user/" + userId + "/account", HttpMethod.GET, makeAuthEntity(),Account.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		return account;
		
	}
	
	public void withdraw(int fromUserId, BigDecimal amount) {
		
		try {
		restTemplate.exchange(BASE_URL + "/withdraw", HttpMethod.GET, makeAuthEntity(),Account.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	public void add(int toUserId, BigDecimal amount) {
		
		try {
		restTemplate.exchange(BASE_URL + "/receive", HttpMethod.GET, makeAuthEntity(),Account.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
	}

private HttpEntity makeAuthEntity() {
	HttpHeaders headers = new HttpHeaders();
	headers.setBearerAuth(TOKEN);
	HttpEntity entity = new HttpEntity<>(headers);
	return entity;
}
}