package com.jdata.db.jdata.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDao {
	Statement statement;
	PreparedStatement preparedStatement;
	
	String unitTestDBIdentifier = "";
	
	public void setUnitTestDBIdentifier(String unitTestDBIdentifier) {
		this.unitTestDBIdentifier = unitTestDBIdentifier;
	}
	
	public String getUnitTestDBIdentifier() {
		return this.unitTestDBIdentifier;
	}
	
	public void beginTransaction() {
		ConnectionFactory.beginTransaction();
	}
	
	public void commitTransaction() {
		ConnectionFactory.commitTransaction();
		close();
	}
	
	public void rollbackTransaction() {
		ConnectionFactory.rollbackTransaction();
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		preparedStatement = ConnectionFactory.getConnection(unitTestDBIdentifier).prepareStatement(sql);
		return preparedStatement;
	}
	
	public ResultSet executeQuery(String sql) throws SQLException {
		statement = ConnectionFactory.getConnection(unitTestDBIdentifier).createStatement();
		return statement.executeQuery(sql);
	}
	
	public void executeUpdate(String sql) {
		try {
			statement = ConnectionFactory.getConnection(unitTestDBIdentifier).createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			this.close();
		}
	}

	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ConnectionFactory.closeConnection();
	}
	
}
