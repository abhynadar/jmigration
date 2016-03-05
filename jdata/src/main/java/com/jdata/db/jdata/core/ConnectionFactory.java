package com.jdata.db.jdata.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdata.db.jdata.helpers.ConfigHelper;

public class ConnectionFactory {
	static ConnectionFactory instance;
	ConfigHelper config;
	static Connection connection;
	static boolean isTransactionInProgress = false;
	static String unitTestDBIdentifier = "";
	
	private ConnectionFactory() {
		try {
			config = new ConfigHelper();
			Class.forName(config.getDBDriverClass());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection createConnection() {
		try {
			if (connection == null || (connection != null && connection.isClosed())) {
				connection = DriverManager.getConnection(config.getDBUrl() + config.getDBName() + unitTestDBIdentifier, config.getDBUser(), config.getDBPwd());
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		return connection;
	}
	
	
	public static Connection getConnection(String unitTestDBIdentifierToUse) {
		unitTestDBIdentifier = unitTestDBIdentifierToUse;
		if (instance == null) {
			instance = new ConnectionFactory();
		}
		return instance.createConnection();
	}
	
	public static void beginTransaction() {
		try {
			if (isTransactionInProgress) {
				return;
			}
			if (connection == null) {
				getConnection(unitTestDBIdentifier);
			}
			if (connection != null && !connection.isClosed()) {
				connection.setAutoCommit(false);
				isTransactionInProgress = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void commitTransaction() {
		try {
			if (connection != null && !connection.isClosed() && isTransactionInProgress) {
				connection.commit();
				isTransactionInProgress = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollbackTransaction() {
		try {
			if (connection != null && !connection.isClosed() && isTransactionInProgress) {
				connection.rollback();
				isTransactionInProgress = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("static-access")
	public static void close() {
		closeConnection();
		if (instance != null) {
			instance.close();
		}
	}

	public static void closeConnection() {
		if (connection != null && !isTransactionInProgress) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	
	public static String GetFullDBName(String dbName) {
		ConfigHelper config = new ConfigHelper();
		return config.getDBName() + dbName;
	}
	
	public static void CreateDatabase(String dbName) {
		String sql = "CREATE DATABASE " + GetFullDBName(dbName);
		ExecuteExclusiveStatement(sql);
	}
	
	public static void DropDatabase(String dbName) {
		String sql = "DROP DATABASE " + GetFullDBName(dbName);
		ExecuteExclusiveStatement(sql);
	}
	
	public static void ExecuteExclusiveStatement(String sql) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = CreateConnectionForDBCreation();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Connection CreateConnectionForDBCreation() {
		ConfigHelper config = new ConfigHelper();
		try {
			Class.forName(config.getDBDriverClass());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			return DriverManager.getConnection(config.getDBUrl(), config.getDBUser(), config.getDBPwd());
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	
}
