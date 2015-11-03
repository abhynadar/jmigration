package com.jdata.db.jdata.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jdata.db.jdata.helpers.ConfigHelper;

public class ConnectionFactory {
	static ConnectionFactory instance;
	ConfigHelper config;
	static Connection connection;
	static boolean isTransactionInProgress = false;
	
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
				connection = DriverManager.getConnection(config.getDBUrl() + config.getDBName(), config.getDBUser(), config.getDBPwd());
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		return connection;
	}
	
	public static Connection getConnection() {
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
				getConnection();
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

}
