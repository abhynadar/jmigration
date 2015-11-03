package com.jmigration.db.jmigration;

import com.jmigration.db.jmigration.support.DummyMigration1;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MigrationTest extends TestCase {

	public MigrationTest(String testName) {
		super(testName);
	}
	
    public static Test suite()
    {
        return new TestSuite( MigrationTest.class );
    }

    public void testUpMigration() {
    	DummyMigration1 migration = new DummyMigration1();
    	String expectedValue = "Alter TABLE VersionInfo MODIFY VersionDescription VARCHAR(500) NULL";
    	assertEquals(expectedValue, migration.up());
    }
    
    public void testDownMigration() {
    	DummyMigration1 migration = new DummyMigration1();
    	String expectedValue = "Alter TABLE VersionInfo MODIFY VersionDescription VARCHAR(500) NULL";
    	assertEquals(expectedValue, migration.down());
    }
    
}
