package com.derrick.covington.data;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Resource
public interface DataStore {

	List<Map<String, String>> getData();

	boolean assignIp(String ip);

	boolean releaseIp(String ip);

	boolean createNewRecords(Map<String,String> records);

}
