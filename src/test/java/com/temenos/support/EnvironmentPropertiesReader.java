package com.temenos.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.testng.log4testng.Logger;

/**
 * EnvironmentPropertiesReader is to set the environment variable declaration mapping for config properties in the UI test
 */
public class EnvironmentPropertiesReader {

	private static final Logger log = Logger.getLogger(EnvironmentPropertiesReader.class);
	private static EnvironmentPropertiesReader envProperties;
	private String configFilePath = "./src/main/resources/config.properties";
	private Properties properties;

	private EnvironmentPropertiesReader() {		
		properties = loadProperties();
	}

	private Properties loadProperties() {
		File file = new File(configFilePath);
		FileInputStream fileInput = null;
		Properties props = new Properties();

		try {
			fileInput = new FileInputStream(file);
			props.load(fileInput);
			fileInput.close();
		}
		catch (FileNotFoundException e) {
			log.error("config.properties is missing or corrupt : " + e.getMessage());
		}
		catch (IOException e) {
			log.error("read failed due to: " + e.getMessage());
		}

		return props;
	}

	public static EnvironmentPropertiesReader getInstance() {
		if (envProperties == null) {
			envProperties = new EnvironmentPropertiesReader();
		}
		return envProperties;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public boolean hasProperty(String key) {
		return StringUtils.isNotBlank(properties.getProperty(key));
	}
	
	public String getBrowserName() {
		if(properties.getProperty("browserName").equals("ie")) {
			return "iexplorer";	
		} else {
			return properties.getProperty("browserName");
		}	
	}
	
	public String getBrowserVersion() {
		return properties.getProperty("browserVersion");
	}
	
	public String getPlatform() {
		return properties.getProperty("platform");
	}
	
	public boolean isRunningLocalGrid() {
		return Boolean.valueOf(properties.getProperty("runLocalGrid"));
	}
	
	public String getURL() {
		return properties.getProperty("url");
	}
	
	public String getAuthUsername() {
		return properties.getProperty("authUsername");
	}
	
	public String getAuthPassword() {
		return properties.getProperty("authPassword");
	}
	
	public String getUsername() {
		return properties.getProperty("username");
	}
	
	public String getPassword() {
		return properties.getProperty("password");
	}
	
	public String getDeviceHost() {
		return properties.getProperty("deviceHost");
	}
	
	public String getDevicePort() {
		return properties.getProperty("devicePort");
	}
}
