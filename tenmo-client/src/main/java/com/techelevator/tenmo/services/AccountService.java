package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;

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
			account = restTemplate
					.exchange(BASE_URL + "user/" + userId + "/account", HttpMethod.GET, makeAuthEntity(), Account.class)
					.getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		return account;

	}

	public void update(Account account) {
		try {
			restTemplate.exchange(BASE_URL + "update", HttpMethod.PUT, makeAccountEntity(account), Account.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getUsernameFromAccountId(int accountId) {
		String username = "";
		try {
			username = restTemplate.exchange(BASE_URL + "username/" + accountId, HttpMethod.GET, makeAuthEntity(), String.class).getBody();
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		return username;
	}
	
	 private HttpEntity<Account> makeAccountEntity(Account account) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(TOKEN);
	        HttpEntity<Account> entity = new HttpEntity<>(account, headers);
	        return entity;
	      }

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}