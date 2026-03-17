package com.apps.quantitymeasurement.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionPool {
	private static final Logger logger = Logger.getLogger(
			ConnectionPool.class.getName());
	
	private static ConnectionPool instance;
	private List<Connection> availableConnections;
	private List<Connection> usedConnection;
	private final int poolSize;
	private final String dbUrl;
	private final String dbUsername;
	private final String dbPassword;
	private final String driverClass;
	private final String testQuery;
	
	private ConnectionPool() throws SQLException{
		ApplicationConfig config = ApplicationConfig.getInstance();
		
		this.poolSize = config.getIntProperty("db.pool-size", 5);
		this.dbUrl = config.getProperty("db.url");
		this.dbUsername = config.getProperty("db.username");
		this.dbPassword = config.getProperty("db.password");
		this.driverClass = config.getProperty("db.driver");
		this.testQuery = config.getProperty("db.hikari.connection-test-query", "SELECT 1");
		
		this.availableConnections = new ArrayList<>();
		this.usedConnection = new ArrayList<>();
		
		try {
			Class.forName(driverClass);
		}
		catch(ClassNotFoundException e) {
			throw new SQLException("Database driver not found",e);
		}
		
		initializeConnections();
	}
	
	public static synchronized ConnectionPool getInstance() throws SQLException{
		if(instance==null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	public void initializeConnections() throws SQLException{
		
		for(int i=0; i<poolSize; i++) {
			availableConnections.add(createConnection());
		}
		logger.info("Connection pool intialized with size: "+poolSize);
	}
	public Connection createConnection() throws SQLException{
		Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		
		logger.info("New database connection created");
		return con;
	}
	public synchronized Connection getConnection() throws SQLException{
		if(!availableConnections.isEmpty()) {
			Connection con = availableConnections.remove(availableConnections.size()-1);
			
			usedConnection.add(con);
			return con;
		}
		if(usedConnection.size()<poolSize) {
			Connection con = createConnection();
			usedConnection.add(con);
			return con;
		}
		throw new SQLException("Maximun pool size reached");
	}
	
	public synchronized void releaseConnection(Connection connection) {
		if(connection==null)
			return;
		usedConnection.remove(connection);
		availableConnections.add(connection);
	}
	
	public boolean validateConnection(Connection connection) {
		try(Statement stmt = connection.createStatement()){
			stmt.execute(this.testQuery);
			return true;
		}
		catch(SQLException e) {
			logger.warning("Connection validation failed: "+e.getMessage());
			return false;
		}
	}
	public synchronized void closeAll() {
		for(Connection con : availableConnections) {
			try {
				con.close();
			}
			catch(SQLException e) {
				logger.warning("Error closing available connection: "+e.getMessage());
			}
		}
		
		for(Connection con : usedConnection) {
			try {
				con.close();
			}
			catch(SQLException e) {
				logger.warning("Error closing used connection: "+e.getMessage());
			}
		}
		availableConnections.clear();
		usedConnection.clear();
		
		logger.info("All database connection closed");
	}
	
	public int getAvailableConnectionCount() {
		return availableConnections.size();
	}
	public int getUsedConnectionCount() {
		return usedConnection.size();
	}
	public int getTotalConnectionCount() {
		return getAvailableConnectionCount()+getUsedConnectionCount();
	}

	@Override
	public String toString() {
		return "ConnectionPool [availableConnections=" + availableConnections + ", usedConnection=" + usedConnection
				+ ", poolSize=" + poolSize + ", dbUrl=" + dbUrl + ", dbUsername=" + dbUsername + ", dbPassword="
				+ dbPassword + ", driverClass=" + driverClass + ", testQuery=" + testQuery + "]";
	}
	
	public static void main(String[] args) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection con = pool.getConnection();
			logger. info("Validate connection: " + (pool. validateConnection(con) ? "Success" : "Failure") );
			logger. info("Available connections after acquiring 1: " + pool.getAvailableConnectionCount());
			logger. info("Used connections after acquiring 1: " + pool.getUsedConnectionCount());
			pool. releaseConnection(con);
			logger. info("Available connections after releasing 1: " + pool.getAvailableConnectionCount());
			logger. info("Used connections after releasing 1: " + pool.getUsedConnectionCount() );
			pool. closeAll() ;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
