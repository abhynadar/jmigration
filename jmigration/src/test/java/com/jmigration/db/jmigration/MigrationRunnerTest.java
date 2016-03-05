package com.jmigration.db.jmigration;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class MigrationRunnerTest 
{
	static MigrationRunner runner = null;
	static String testRunDBName = "test";

/*	
	public MigrationRunnerTest(String testName) {
		super(testName);
	}
	
    public static Test suite()
    {
        return new TestSuite( MigrationRunnerTest.class );
    }
*/ 
	
    @BeforeClass
    public static void setUp() {
    	runner = new MigrationRunner(testRunDBName, true);
    }
    
    @AfterClass
    public static void tearDown() {
    	if (runner != null) {
    		runner.close(true);
    	}
    	runner = null;
    }
    
    @Test
    public void testMigrationClassListing() {
    	//MigrationRunner runner = new MigrationRunner("");
    	List<String> classList = runner.getMigrationClassList();
    	assertEquals(3, classList.size());
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration1"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration2"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigrationQA"));
    }
    
    @Test
    public void testMigrationClassListingByTagsForAll() {
    	//MigrationRunner runner = new MigrationRunner("");
    	List<String> tags = new ArrayList<String>();
    	tags.add("ALL");

    	List<String> classList = new ArrayList<String>();
    	Hashtable<Long, String> classTable = null;
		try {
			classTable = runner.getMigrationClassListByTags(tags);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<Long> enumeration = classTable.keys();
		while (enumeration.hasMoreElements()) {
			classList.add(classTable.get(enumeration.nextElement()));
		}
    	
    	assertEquals(3, classList.size());
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration1"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration2"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigrationQA"));
    }

    @Test
    public void testMigrationClassListingByTagsForDEV() {
    	//MigrationRunner runner = new MigrationRunner("");
    	List<String> tags = new ArrayList<String>();
    	tags.add("DEV");
    	
    	List<String> classList = new ArrayList<String>();
    	Hashtable<Long, String> classTable = null;
		try {
			classTable = runner.getMigrationClassListByTags(tags);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<Long> enumeration = classTable.keys();
		while (enumeration.hasMoreElements()) {
			classList.add(classTable.get(enumeration.nextElement()));
		}
    	    	
    	assertEquals(2, classList.size());
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration1"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration2"));
    }

    @Test
    public void testMigrationClassListingByTagsForQA() {
    	//MigrationRunner runner = new MigrationRunner("");
    	List<String> tags = new ArrayList<String>();
    	tags.add("QA");

    	List<String> classList = new ArrayList<String>();
    	Hashtable<Long, String> classTable = null;
		try {
			classTable = runner.getMigrationClassListByTags(tags);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<Long> enumeration = classTable.keys();
		while (enumeration.hasMoreElements()) {
			classList.add(classTable.get(enumeration.nextElement()));
		}
    	    	
    	assertEquals(2, classList.size());
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration1"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigrationQA"));
    }

    @Test
    public void testMigrationClassListingByTagsForDEVAndQA() {
    	//MigrationRunner runner = new MigrationRunner("");
    	List<String> tags = new ArrayList<String>();
    	tags.add("DEV");
    	tags.add("QA");

    	List<String> classList = new ArrayList<String>();
    	Hashtable<Long, String> classTable = null;
		try {
			classTable = runner.getMigrationClassListByTags(tags);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<Long> enumeration = classTable.keys();
		while (enumeration.hasMoreElements()) {
			classList.add(classTable.get(enumeration.nextElement()));
		}
    	
    	
    	assertEquals(3, classList.size());
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration1"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigration2"));
    	assertEquals(true, classList.contains("com.jmigration.db.jmigration.support.DummyMigrationQA"));
    }
    
    /*
    public void testRunMigrationClassByName(){
    	MigrationRunner runner = new MigrationRunner();
				try {
					assertTrue(runner.run(true, "com.jmigration.db.jmigration.support.DummyMigration1", null));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
    */

    @Test
    public void testScanAndRunMigrationUpByTagsDEV(){
    	//MigrationRunner runner = new MigrationRunner("");
    	List<String> tags = new ArrayList<String>();
    	tags.add("DEV");
			try {
				assertTrue(runner.scanAndRunByTags(true, tags));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    @Test
    public void testScanAndRunMigrationDownByTagsDEV(){
    	//MigrationRunner runner = new MigrationRunner("");
    	List<String> tags = new ArrayList<String>();
    	tags.add("DEV");
			try {
				assertTrue(runner.scanAndRunByTags(false, tags));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
}
