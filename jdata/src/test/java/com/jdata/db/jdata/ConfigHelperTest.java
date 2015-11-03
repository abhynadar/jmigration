package com.jdata.db.jdata;

import java.io.IOException;

import com.jdata.db.jdata.helpers.ConfigHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ConfigHelperTest extends TestCase {
	
    public ConfigHelperTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( ConfigHelperTest.class );
    }

    public void testGetConfigValues() {
    	ConfigHelper config = new ConfigHelper();
    	try {
			config.LoadConfigValues();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	assertNotNull(config.getDBUser());
    	assertNotNull(config.getDBPwd());
    	assertNotNull(config.getDBDriverClass());
    	assertNotNull(config.getDBUrl());
    	assertNotNull(config.getDBName());
    }
}
