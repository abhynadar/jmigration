package com.jmigration.db.jmigration;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class JLogger {

	static final Logger logger = Logger.getRootLogger(); //Logger.getLogger(JLogger.class);
	
	public JLogger() {
		//BasicConfigurator.configure();
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
	}
	
	public String infoLog(String message, Object...args) {
		String messageToLog = String.format(message, args);
		try {
			logger.info(messageToLog);
		} catch(Exception exception) {
			//do not throw this error. We want app to function even if unable to log for now
		}
		return messageToLog;
	}

	public String errorLog(Exception exceptionToLog, String message, Object...args) {
		String messageToLog = String.format(message, args);
		//messageToLog = messageToLog + " " + String.format("Exception details: message - %s, stacktrace - %s", exceptionToLog.getMessage(), stackTraceToString(exceptionToLog));
		try {
			logger.error(messageToLog, exceptionToLog);
		} catch(Exception exception) {
			messageToLog = messageToLog + " " + String.format("Log Exception details: message - %s, stacktrace - %s", exception.getMessage(), stackTraceToString(exception));
			//do not throw this error. We want app to function even if unable to log for now
		}
		return messageToLog;
	}
	
	public String stackTraceToString(Throwable t) {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    t.printStackTrace(pw);
	    return sw.toString();
	}

}
