package com.techelevator;

import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.AccountSqlDAO;

public class AccountSqlDAOIntegrationTest extends BaseDAOTests {
	
	private AccountDAO dao;
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	
	@Before
	public void setup() {
		dao = new AccountSqlDAO(jdbcTemplate);
	}

//	getBalance
//	updateBalance
//	getUsernameFromAccountId
}
