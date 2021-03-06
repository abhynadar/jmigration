package com.jdata.db.jdata.helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {
	
	public ConfigHelper() {
		try {
			LoadConfigValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDBUser() {
		return dbUser;
	}

	public String getDBPwd() {
		return dbPwd;
	}

	public String getDBDriverClass() {
		return dbDriverClass;
	}

	public String getDBUrl() {
		return dbUrl;
	}

	public String getDBName() {
		return dbName;
	}

	public void setPropertyFileName(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}

	String propertyFileName = "connection.properties";
	
	static String dbUser = "red";
	static String dbPwd = "red001";
	static String dbDriverClass = "com.mysql.jdbc.Driver";
	static String dbUrl = "jdbc:mysql://localhost/";
	static String dbName = "yshopdb_dev";

	public void LoadConfigValues() throws IOException {
		InputStream stream = null;
		
		try {
			Properties props = new Properties();
			
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			stream = loader.getResourceAsStream(propertyFileName);
			//stream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			
			if (stream != null) {
				props.load(stream);
			} else {
				throw new FileNotFoundException("Property file " + propertyFileName + "not found in the classpath.");
			}
			
			if (props.containsKey("DBUser")) {
				dbUser = props.getProperty("DBUser");
			}
			if (props.containsKey("DBPwd")) {
				dbPwd = props.getProperty("DBPwd");
			}
			if (props.containsKey("DBDriverClass")) {
				dbDriverClass = props.getProperty("DBDriverClass");
			}
			if (props.containsKey("DBUrl")) {
				dbUrl = props.getProperty("DBUrl");
			}
			if (props.containsKey("DBName")) {
				dbName = props.getProperty("DBName");
			}
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}finally{
			if (stream != null) {
				stream.close();
			}
		}
		
		
	}

}
