package com.derrick.covington.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.derrick.covington.service.IPAddressService;

@RestController
public class IPAddressController {
	
	@Autowired
	IPAddressService addressService;


	@PostMapping("/addNewAddresses")
	public ResponseEntity<String> addNewAddress(
			@RequestParam(name ="cidrBlock", required=true) String cidr){
		return addressService.addNewAddresses(cidr);
	}
	
	@GetMapping("/getIpList")
	public ResponseEntity<List<Map<String,String>>> getIpAddressList(){
		return addressService.getIpAddressList();
	}
	
	@PutMapping("/allocateIpAddress")
	public ResponseEntity<String> acquireIpAddress(@RequestParam(name ="ipAddress", required=true)String ip){
		return addressService.acquireIpAddress(ip);
	}
	
	@PutMapping("/releaseIpAddress")
	public ResponseEntity<String> releaseIpAddress(@RequestParam(name ="ipAddress", required=true)String ip){
		return addressService.releaseIpAddress(ip);
}
	}