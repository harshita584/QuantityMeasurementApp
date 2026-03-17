package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import com.apps.quantitymeasurement.util.ConnectionPool;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository{
	
	private static final Logger logger = 
			Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());
	
	private static QuantityMeasurementDatabaseRepository instance;
	
	private static final String INSERT_QUERY = 
			"INSERT INTO quantity_measurement_entity "+
			"(this_value, this_unit, this_measurement_type, that_value, that_unit, "+
					"that_measurement_type, operation, result_value, result_unit, "+
			"result_measurement_type, result_string, is_error, error_message, "+
			"created_at, updated_at)" +
			"values(?, ?, ?, ?,?,?,?,?,?,?,?,?,?, NOW(), NOW())";
	
	private static final String SELECT_ALL_QUERY = 
			"SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";
	
	private static final String SELECT_BY_OPERARION = 
			"SELECT * from quantity_measurement_entity WHERE operation =? ORDER BY created_at DESC";
	
	private static final String SELECT_BY_MEASUREMENT_TYPE =
			"SELECT * FROM quantity_measurement_entity "+
			"WHERE this_measurement_type = ? ORDER BY created_at DESC";
	
	private static final String DELETE_ALL_QUERY =
			"DELETE FROM quantity_measurement_entity";
	
	private static final String COUNT_QUERY =
			"SELECT COUNT(*) FROM quantity_measurement_entity";
	
	private ConnectionPool connectionPool;
	
	private QuantityMeasurementDatabaseRepository() {
		
	}
	public void intializeDatabase() {
		String createTableQuery =
	            "CREATE TABLE IF NOT EXISTS quantity_measurement_entity ("
	                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
	                    + "this_value DOUBLE,"
	                    + "this_unit VARCHAR(50),"
	                    + "this_measurement_type VARCHAR(50),"
	                    + "that_value DOUBLE,"
	                    + "that_unit VARCHAR(50),"
	                    + "that_measurement_type VARCHAR(50),"
	                    + "operation VARCHAR(50),"
	                    + "result_value DOUBLE,"
	                    + "result_unit VARCHAR(50),"
	                    + "result_measurement_type VARCHAR(50),"
	                    + "result_string VARCHAR(255),"
	                    + "is_error BOOLEAN,"
	                    + "error_message VARCHAR(255),"
	                    + "created_at TIMESTAMP,"
	                    + "updated_at TIMESTAMP"
	                    + ")";

	    Connection conn = null;
	    Statement stmt = null;
	    
	    try {
	    	conn = connectionPool.getConnection();
	    	stmt = conn.createStatement();
	    	stmt.execute(createTableQuery);
	    	
	    	logger.info("Database table initialized successfully");
	    }
	    catch(Exception e) {
	    	logger.severe("Error intializing database: "+e.getMessage());
	    }
	    finally {
	    	closeResources(stmt, conn);
	    }
	}
	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
		if(instance==null) {
			instance = new QuantityMeasurementDatabaseRepository();
			try {
				instance.connectionPool = ConnectionPool.getInstance();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			instance.intializeDatabase();
		}
		return instance;
	}
	
	@Override 
	public void save(QuantityMeasurementEntity entity) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = connectionPool.getConnection();
			pstmt = conn.prepareStatement(INSERT_QUERY);
			
			pstmt.setDouble(1, entity.thisValue);
			pstmt.setString(2, entity.thisUnit);
			pstmt.setString(3, entity.thisMeasurementType);
			
			pstmt.setDouble(4,entity.thatValue);
			pstmt.setString(5, entity.thatUnit);
			pstmt.setString(6, entity.thatMeasurementType);
			
			pstmt.setString(7, entity.operation);
			
			pstmt.setDouble(8, entity.resultValue);
			pstmt.setString(9, entity.thatMeasurementType);
			pstmt.setString(10, entity.resultMeasurementType);
			
			pstmt.setString(11, entity.resultString);
			pstmt.setBoolean(12, entity.isError);
			pstmt.setString(13, entity.errorMessage);
			
			pstmt.executeUpdate();
			
			logger.info("Measurement saved successfully");
			
		}
		catch(SQLException e) {
			logger.severe("Error saving measurement: "+e.getMessage());
		}
		finally {
			closeResources(pstmt, conn);
		}
	}
	
	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		Connection con = null;
		PreparedStatement statement = null;
		
		List<QuantityMeasurementEntity> result = new ArrayList<>();
		
		try {
			con = connectionPool.getConnection();
			statement = con.prepareStatement(SELECT_ALL_QUERY);
			
			ResultSet res = statement.executeQuery();
			
			while(res.next()) {
				result.add(mapResultSetToEntity(res));
			}
		}
		catch(SQLException e) {
			logger.severe("Error saving measurement: " + e.getMessage());
		}
		finally {
	        closeResources(statement, con);
	    }
		return result;
	}
	
	public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation){
	    List<QuantityMeasurementEntity> result = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = connectionPool.getConnection();
	        pstmt = conn.prepareStatement(SELECT_BY_OPERARION);
	        pstmt.setString(1, operation);

	        ResultSet rs = pstmt.executeQuery();

	        while(rs.next()){
	            result.add(mapResultSetToEntity(rs));
	        }
	    } catch(SQLException e){
	        logger.severe("Error retrieving by operation: " + e.getMessage());
	    } finally {
	        closeResources(pstmt, conn);
	    }

	    return result;
	}
	public List<QuantityMeasurementEntity> getMeasurementByType(String measurementType){
	    List<QuantityMeasurementEntity> result = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try{
	        conn = connectionPool.getConnection();
	        pstmt = conn.prepareStatement(SELECT_BY_MEASUREMENT_TYPE);
	        pstmt.setString(1, measurementType);

	        ResultSet rs = pstmt.executeQuery();

	        while(rs.next()){
	            result.add(mapResultSetToEntity(rs));
	        }
	    }
	    catch(SQLException e){
	        logger.severe("Error retrieving by measurement type: " + e.getMessage());
	    }
	    finally{
	        closeResources(pstmt, conn);
	    }

	    return result;
	}
	
	public int getTotalCount() {
		Connection conn =null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = connectionPool.getConnection();
			stmt = conn.createStatement();
			rs =stmt.executeQuery(COUNT_QUERY);
			
			if(rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException e) {
			logger.severe("Error counting measurement: "+e.getMessage());
		}
		finally {
			closeResources(rs, stmt, conn);
		}
		return 0;
	}
	
	public void deleteAll() {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = connectionPool.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(DELETE_ALL_QUERY);
			
			logger.info("All measurement deleted");
		}
		catch(SQLException e) {
			logger.severe("Error deleting measurement: "+e.getMessage());
		}
		finally{
			closeResources(stmt, conn);
		}
	}
	
	private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) {

	    try {
	        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(rs.getDouble("this_value"), rs.getString("this_unit"), rs.getString("this_measurement_type"), rs.getDouble("that_value"), rs.getString("that_unit"), rs.getString("that_measurement_type"), rs.getString("operation"), rs.getDouble("result_value"), rs.getString("result_unit"), rs.getString("result_measurement_type"), rs.getString("result_string"), rs.getBoolean("is_error"), rs.getString("error_message"));
	        return entity;

	    } catch (SQLException e) {
	        logger.severe("Error mapping result set: " + e.getMessage());
	        return null;
	    }
	}
	
	public String getPoolStatistics() {
		
		return "Available Connections: "
	            + connectionPool.getAvailableConnectionCount()
	            + ", Used Connections: "
	            + connectionPool.getUsedConnectionCount()
	            + ", Total: "
	            + connectionPool.getTotalConnectionCount();
	}
	
	public void closeResources(Statement stmt, Connection conn) {
	    try {
	        if(stmt != null) stmt.close();

	        if(conn != null) {
	            connectionPool.releaseConnection(conn);
	        }

	        logger.info("resources closed successfully");
	    }
	    catch(SQLException e) {
	        logger.severe("Error occurred cannot close resources: " + e.getMessage());
	    }
	}
	public void closeResources(ResultSet rs, Statement stmt, Connection conn) {
	    try {
	        if(rs != null) rs.close();
	        if(stmt != null) stmt.close();

	        if(conn != null) {
	            connectionPool.releaseConnection(conn);
	        }

	        logger.info("resources closed successfully");
	    }
	    catch(SQLException e) {
	        logger.severe("Error occurred cannot close resources: " + e.getMessage());
	    }
	}
}
