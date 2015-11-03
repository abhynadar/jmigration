package com.jmigration.db.jmigration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LoggerTest extends TestCase {

	public LoggerTest(String testName) {
		super(testName);
	}
	
    public static Test suite()
    {
        return new TestSuite( LoggerTest.class );
    }
    
    public void testInfoLog() {
    	JLogger jlogger = new JLogger();
    	String information = jlogger.infoLog("This is my info logger %s", "param1");
    	assertEquals("This is my info logger param1", information);
    }

    public void testErrorLog() {
    	JLogger jlogger = new JLogger();
    	try {
    		Integer.parseInt("not a number");
    	} catch(NumberFormatException exception) {
        	String information = jlogger.errorLog(exception, "This is my error logger %s", "param1");
        	//System.out.println(information);
        	assertNotNull(information);
    	}
    	//assertEquals("This is my info logger param1", information);
    }

}
