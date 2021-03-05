package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfer;

public class TransferService {

	// I'm not sure how we actually get the token here, is that something that rest
	// or spring handles?
	// does this need to be public like example in m02d13 client AuctionService?
	private static String TOKEN = "";
	private final String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();

	public TransferService(String url) {
		this.BASE_URL = url;
	}

	// what is the purpose of the getBody() after the restTemplate.exchange?
	public Transfer getTransferById(int transferId) {
		Transfer transfer = new Transfer();               

		try {
			transfer = restTemplate.exchange(BASE_URL + "transfer/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();

		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		return transfer;
	}

	public Transfer[] getAllTransfersByUserId(int userId) {
		Transfer[] transfers = null;

		try {
			transfers = restTemplate.exchange(BASE_URL + "user/" + userId + "/transfer", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();

		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {

			System.out.println(e.getMessage());
		}
		return transfers;
	}
	
	public Transfer transfer(int fromUserId, int toUserId, BigDecimal amount) {
		Transfer transfer = new Transfer();
		transfer.setTypeId(2);
		transfer.setStatusId(2);
		transfer.setFrom(fromUserId);
		transfer.setTo(toUserId);
		transfer.setAmount(amount);
		
		try {
			transfer = restTemplate.exchange(BASE_URL + "transfer", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class).getBody();

		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " " + e.getStatusText());

		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		return transfer;
	}

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
      }

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
