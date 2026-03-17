package com.apps.quantitymeasurement.exception;
import java.sql.*;

public class DatabaseException extends SQLException{
	
	public DatabaseException(String msg) {
		super(msg);
	}
	
	public DatabaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public static DatabaseException connectionFailed(String details, Throwable cause) {
		return new DatabaseException("Database connection failed: "+ details, cause);
	}
	
	public static DatabaseException queryFailed(String query, Throwable cause) {
		return new DatabaseException("Query execution failed: "+query, cause);
	}
	
	public static DatabaseException transactionFailed(String operation, Throwable cause) {
		return new DatabaseException("Transaction failed during "+operation, cause);
	}
	public static void main(String[] args) {
		try {
			throw connectionFailed("Unable to connect to the database", new RuntimeException("Connection timeout"));
		}
		catch(DatabaseException e) {
			System.out.println("Caught DatabaseException: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
