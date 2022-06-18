package com.derrick.covington;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.derrick.covington.data.DataStore;
import com.derrick.covington.data.ExcelDataStore;

@Configuration
@ComponentScan("com.derrick.covington")
public class DataConfig {

	@Bean
	DataStore dataStore() {
		return new ExcelDataStore("src/main/resources/IpAddressDataStore.xlsx");	
	}
}
