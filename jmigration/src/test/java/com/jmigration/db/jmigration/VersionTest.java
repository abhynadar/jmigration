package com.jmigration.db.jmigration;

import com.jmigration.db.jmigration.core.Version;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class VersionTest extends TestCase {

	public VersionTest(String testName) {
		super(testName);
	}
	
    public static Test suite()
    {
        return new TestSuite( VersionTest.class );
    }

    public void testGetVersionForSingleDigit() {
    	Version version = new Version(1, 1, 0, 0, 2015, 1, 1, 1, 1);
    	assertEquals(10100201501010101L, version.getVersion());
    }
    
    public void testGetVersionForDoubleDigit() {
    	Version version = new Version(1, 10, 1, 1, 2015, 10, 10, 10, 10);
    	assertEquals(11011201510101010L, version.getVersion());
    }
}
