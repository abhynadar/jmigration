package com.jmigration.db.jmigration;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UtilityTest extends TestCase {

	public UtilityTest(String testName) {
		super(testName);
	}
	
    public static Test suite()
    {
        return new TestSuite( UtilityTest.class );
    }

    public void testToCsvStringWithListOfStringValues() {
    	List<String> cities = new ArrayList<String>();
    	cities.add("delhi");
    	cities.add("jamnagar");
    	cities.add("pune");
    	assertEquals("delhi,jamnagar,pune", Utility.ToCsvString(cities));
    }
    
    public void testToCsvStringWithNullList() {
    	assertEquals("", Utility.ToCsvString(null));
    }

    public void testToCsvStringWithEmptyList() {
    	List<String> cities = new ArrayList<String>();
    	assertEquals("", Utility.ToCsvString(cities));
    }
}
