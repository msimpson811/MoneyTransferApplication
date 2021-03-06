package com.techelevator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.User;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class UserSqlDAOIntegrationTest extends BaseDAOTests {
	
	private UserDAO dao;
	private JdbcTemplate jdbcTemplate;
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new UserSqlDAO(jdbcTemplate);
		
//		String sqlTruncateUsers = "TRUNCATE TABLE users CASCADE";
//		
//		jdbcTemplate.update(sqlTruncateUsers);
//		
		user1.setUsername("One");
		user2.setUsername("Two");
		user3.setUsername("Three");
		user1.setId(10000L);
		user2.setId(20000L);
		user3.setId(30000L);
		
		String sqlAddUser = "INSERT INTO users (user_id, username) VALUES (?, ?);";
		
		jdbcTemplate.update(sqlAddUser, user1.getId(), user1.getUsername());
		jdbcTemplate.update(sqlAddUser, user2.getId(), user2.getUsername());
		jdbcTemplate.update(sqlAddUser, user3.getId(), user3.getUsername());
		
	}
	
//	findIdByUsername
//	findAll
//	findByUsername
//	create
	
	@Test
	public void findIdByUsername_returns_the_correct_id() {
		//Arrange
		long oneResult = user1.getId();
		long twoResult = user2.getId();
		long threeResult = user3.getId();
				
		//Act
		long user1Result = dao.findIdByUsername("ONE");
		long user2Result = dao.findIdByUsername("Two");
		long user3Result = dao.findIdByUsername("three");
		
		//Assert
		Assert.assertEquals(oneResult, user1Result);
		Assert.assertEquals(twoResult, user2Result);
		Assert.assertEquals(threeResult, user3Result);
		
	}

	@Test
	public void findAll_returns_all_users() {
		//Arrange
		List<User> expected = new ArrayList<>();
		expected.add(user1);
		expected.add(user2);
		expected.add(user3);
		
		//Act
		List<User> result = dao.findAll();
		
		//Assert
		Assert.assertEquals(expected.size(), result.size());
		Assert.assertEquals(expected.get(1).getUsername(), result.get(1).getUsername());
	}
	
	@Test
	public void findByUsername_returns_the_correct_user() {
		//Arrange
		
		
		//Act
		User result1 = dao.findByUsername("one");
		User result2 = dao.findByUsername("TWO");
		User result3 = dao.findByUsername("Three");
		
		//Assert
		Assert.assertEquals(user1.getUsername(), result1.getUsername());
		Assert.assertEquals(user1.getId(), result1.getId());
		Assert.assertEquals(user2.getUsername(), result2.getUsername());
		Assert.assertEquals(user2.getId(), result2.getId());
		Assert.assertEquals(user3.getUsername(), result3.getUsername());
		Assert.assertEquals(user3.getId(), result3.getId());
	}
	
	@Test
	public void create_returns_true_when_account_is_created() {
		//Arrange
		String username = "Testy McTestington";
		String password = "supersecurepassword";
		
		//Act
		boolean result = dao.create(username, password);
		
		//Assert
		Assert.assertTrue("Create shoudl return true when successful", result);
	}
}
