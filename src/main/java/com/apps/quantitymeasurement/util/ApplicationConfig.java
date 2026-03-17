package com.apps.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationConfig {
	private static final Logger logger = Logger.getLogger(
			ApplicationConfig.class.getName()
			);
	
	private static ApplicationConfig instance;
	private Properties properties;
	private Environment environment;
	
	public enum Environment{
		DEVELOPMENT, TESTING, PRODUCTION
	}
	
	public enum ConfigKey{
		REPOSITORY_TYPE("repository_type"),
		DB_DRIVER_CLASS("db.driver"),
		DB_URL("db.url"),
		DB_USERNAME("db.username"),
		DB_POOL_SIZE("db.pool-size"),
		HIKARI_MAX_POOL_SIZE("db.hikari.connection-timeout"),
		HIKARI_IDLE_TIMEOUT("db.hikari.idle-timeout"),
		HIKARI_MAX_LIFETIME("db.hikari.max-lifetime"),
		HIKARI_POOL_NAME("db.hikari.pool-name"),
		HIKARI_CONNECTION_TEST_QUERY("db.hikari.connection-test-query");
		
		public final String key;
		
		ConfigKey(String key) {
			this.key =key;
		}
		private String getKey() {
			return key;
		}
	}
	
	private ApplicationConfig() {
		loadConfiguration();
	}
	
	public static synchronized ApplicationConfig getInstance() {
		if(instance==null)
			instance = new ApplicationConfig();
		return instance;
	}
	
	private void loadConfiguration() {
		properties = new Properties();
		
		try {
			String env = System.getProperty("app.env");
			if(env==null ||  env.isEmpty()) {
				env = System.getenv("APP_ENV");
			}
			
			String configFile = "application.properties";
			InputStream input = ApplicationConfig.class.getClassLoader()
					.getResourceAsStream(configFile);
			
			if(input !=null) {
				properties.load(input);
				logger.info("Configuration loaded from "+configFile);
				
				if(env==null || env.isEmpty()) {
					env = properties.getProperty("app.env", "development");
				}
				this.environment = Environment.valueOf(env.toUpperCase());
			}
			else {
				logger.warning("Configuration file not found, using defaults");
				loadDefaults();
			}
		}
		catch(Exception e) {
			logger.severe("Error loading configuration: "+ e.getMessage());
			loadDefaults();
		}
	}
	
	private void loadDefaults() {
		properties.setProperty("repository.type", "cache");
        properties.setProperty("db.driver", "org.h2.Driver");
        properties.setProperty("db.url", "jdbc:h2:./quantitymeasurementdb");
        properties.setProperty("db.username", "sa");
        properties.setProperty("db.password", "");

        properties.setProperty("db.hikari.maximum-pool-size", "10");
        properties.setProperty("db.hikari.minimum-idle", "2");
        properties.setProperty("db.hikari.connection-timeout", "30000");
        properties.setProperty("db.hikari.idle-timeout", "600000");
        properties.setProperty("db.hikari.max-lifetime", "1800000");
        properties.setProperty("db.hikari.pool-name", "QuantityMeasurementPool");
        properties.setProperty("db.hikari.connection-test-query", "SELECT 1");

        environment = Environment.DEVELOPMENT;
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	public int getIntProperty(String key, int defaultValue) {
		
		try {
			return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
		}catch(NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public String getEnvironment() {
		return environment.name();
	}
	
	public boolean isConfigKey(String key) {
		for(ConfigKey configKey: ConfigKey.values()) {
			if(configKey.getKey().equals(key))
				return true;
		}
		return false;
	}
	
	public void printAllProperties() {
		logger.info("------ Application Configuration ------");
		
		for(String key: properties.stringPropertyNames()) {
			logger.info(key+" = "+properties.getProperty(key));
		}
		
		logger.info("Environment = "+environment);
	}
	
	public static void main(String args[]) {
		ApplicationConfig config = ApplicationConfig.getInstance();
		config.printAllProperties();
	}
}
