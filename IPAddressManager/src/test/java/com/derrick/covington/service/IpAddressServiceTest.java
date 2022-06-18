package com.derrick.covington.service;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("com.derrick.covington")
public class IpAddressServiceTest {
	
	@Autowired
	private IPAddressService service;
	
	@Before
	public void setup() {
		
	}

	@After
	public void tearDown() {
		
	}
	
//	@Test
//	public void testService() {
//	String block = "101.241.15.0/24";
//	Map<String, String> mapList = null;
//		mapList = service.getAddressListFromBlock(block);
//		
//		Assert.assertNotNull(mapList);
//	}
	
	@Test
	public void testAddNewAddresses() {
	String block = "101.241.15.0/24";
	ResponseEntity<String> response = null;
		response = service.addNewAddresses(block);
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testGetIpAddressList() {
		ResponseEntity<List<Map<String,String>>> actualResponse = null;
		actualResponse = service.getIpAddressList();
		assertNotNull(actualResponse.getBody());
		assertFalse(actualResponse.getBody().isEmpty());
		assertTrue(actualResponse.getBody().size()==256 || actualResponse.getBody().size()==257);
	}
	
	@Test
	public void testAcquireIpAddress() {
		String block = "101.241.15.150";
		ResponseEntity<String> response = null;
			response = service.acquireIpAddress(block);
			Assert.assertNotNull(response);
	}
	
	@Test
	public void testReleaseIpAddress() {
		String block = "101.241.15.150";
		ResponseEntity<String> response = null;
			response = service.releaseIpAddress(block);
			Assert.assertNotNull(response);
	}

}
