package com.techelevator;

import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.TransferSqlDAO;

public class TransferSqlDAOIntegrationTest extends BaseDAOTests {
	
	private TransferDAO dao;
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	@Before
	public void setup() {
		dao = new TransferSqlDAO(jdbcTemplate);
	}
	
//	getTransferById
//	getAllTransfersByUserId
//	transfer

}
