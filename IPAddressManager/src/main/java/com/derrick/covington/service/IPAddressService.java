package com.derrick.covington.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.derrick.covington.data.DataStore;

@Service
public class IPAddressService {
	
	@Autowired
	DataStore dataAccessObject;
	

	public ResponseEntity<String> addNewAddresses(String ciderBlock) {
		Map<String, String> ipAddressList = getAddressListFromBlock(ciderBlock);
		HttpStatus status;
		String message;
		if(dataAccessObject.createNewRecords(ipAddressList)==false) {
			status = HttpStatus.CONFLICT;
			message = "Error : failed to create new records";
		}
		else {
			status = HttpStatus.OK;
			message = "Success";
		}
		ResponseEntity <String> response = new ResponseEntity<String>(message,status);
				
		return response;
		
	}

	public Map<String,String> getAddressListFromBlock(String ciderBlock) {
		Map<String,String> ipAddressList = new HashMap<String,String>();
		String[] ciderSplit = ciderBlock.split("/");
		String address = ciderSplit[0];
		int networkPrefixLength = Integer.parseInt(ciderSplit[1]);
		String referenceAddress = address;
		int remainingBitsForIpAllocation = 32 - networkPrefixLength;
		double numberOfIpAddresses = Math.pow(2,remainingBitsForIpAllocation);
		for(int i = 0; i < numberOfIpAddresses; i++) {
			String ipAddress  = getNextIpAddress(referenceAddress);
			ipAddressList.put(ipAddress,"Available");
			referenceAddress = ipAddress;
		}
		return ipAddressList;
	}

	private String getNextIpAddress(String ip) {
		String[] octets = ip.split("\\.");
		for(int k = octets.length - 1; k > -1;k--) {
			int octet = Integer.parseInt(octets[k]);
			if (octet + 1 < 256) {
				octets[k] = String.valueOf(octet + 1);
				break;
			}
		}
		return String.join(".", octets);
	}

	public ResponseEntity<List<Map<String,String>>> getIpAddressList() {
		List<Map<String, String>> data= dataAccessObject.getData();
		ResponseEntity <List<Map<String,String>>> response = new ResponseEntity<List<Map<String,String>>>(data,HttpStatus.OK);
		return response;
	}

	public ResponseEntity<String> acquireIpAddress(String ip) {
		boolean isAssigned = false;
		try {
			isAssigned = dataAccessObject.assignIp(ip);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		HttpStatus status;
		String message;
		if(isAssigned==false) {
			status = HttpStatus.CONFLICT;
			message = "Error : failed to allocate record";
		}
		else {
			status = HttpStatus.OK;
			message = "Success";
		}
		ResponseEntity <String> response = new ResponseEntity<String>(message,status);
		
		return response;
	}
	
	public ResponseEntity<String> releaseIpAddress(String ip) {
		boolean isReleased = false;
		try {
			isReleased = dataAccessObject.releaseIp(ip);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		HttpStatus status;
		String message;
		if(isReleased==false) {
			status = HttpStatus.CONFLICT;
			message = "Error : failed to release record.";
		}
		else {
			status = HttpStatus.OK;
			message = "Success";
		}
		ResponseEntity <String> response = new ResponseEntity<String>(message,status);
		
		return response;
	}

	

}
