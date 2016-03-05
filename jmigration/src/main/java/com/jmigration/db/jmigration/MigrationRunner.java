package com.jmigration.db.jmigration;

import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.jdata.db.jdata.core.ConnectionFactory;
import com.jdata.db.jdata.dao.VersionInfoDao;
import com.jmigration.db.jmigration.core.Tags;

public class MigrationRunner {
	private String packageName = "com.jmigration.db";
	public final static String ALL_ENV = ",ALL,";

	String unitTestDBIdentifier = "";
	
	public MigrationRunner(String unitTestDBIdentifier, boolean createDB) {
		this.unitTestDBIdentifier = unitTestDBIdentifier;
		
		if (createDB) {
			ConnectionFactory.CreateDatabase(unitTestDBIdentifier);
			System.out.println("Created database");
		}
		
		VersionInfoDao versionInfoDao = new VersionInfoDao();
		versionInfoDao.setUnitTestDBIdentifier(unitTestDBIdentifier);
		try {
			if (!versionInfoDao.isVersionInfoAvailable()) {
				versionInfoDao.createVersionInfoTable();
				System.out.println("Created VersionInfo table");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(boolean dropDB) {
		if (dropDB) {
			ConnectionFactory.DropDatabase(unitTestDBIdentifier);
		}
	}
	
	String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public List<String> getMigrationClassList() {
		List<String> classList = new ArrayList<String>();	
		
		Reflections reflections = new Reflections(getPackageName());

		Set<Class<? extends Migration>> migrationClasses = 
		     reflections.getSubTypesOf(com.jmigration.db.jmigration.Migration.class);
		
		for(@SuppressWarnings("rawtypes") Class migration : migrationClasses) {
			classList.add(migration.getName());
		}
		
		return classList;
	}

	public Hashtable<Long, String> getMigrationClassListByTags(List<String> tags) throws InstantiationException, IllegalAccessException {
		Hashtable<Long, String> classTable = new Hashtable<Long, String>();
		
		Boolean isRunningForAllTags = false;
		
		for(int i=0; i < tags.size(); i++) {
			tags.set(i, "," + tags.get(i) + ",");
		}
		if (tags.contains(ALL_ENV)) {
			isRunningForAllTags = true;
		}
		
		Reflections reflections = new Reflections(getPackageName());

		Set<Class<? extends Migration>> migrationClasses = 
		     reflections.getSubTypesOf(com.jmigration.db.jmigration.Migration.class);
		
		for(@SuppressWarnings("rawtypes") Class migration : migrationClasses) {
			
			Migration migrationClass = (Migration) migration.newInstance();
			
			@SuppressWarnings("unchecked")
			Annotation annotation = migration.getAnnotation(Tags.class);
			if (annotation instanceof Tags) {
				if (isRunningForAllTags) {
					classTable.put(migrationClass.migrationVersion().getVersion(), migration.getName());
				}
				else {
					Tags migrationTags = (Tags) annotation;
					String tagsValue = "," + migrationTags.Value().replace(" ", "") + ",";
					for(String listItem : tags) {
						if (tagsValue.contains(listItem)) {
							classTable.put(migrationClass.migrationVersion().getVersion(), migration.getName());
							break;
						}
					}
				}
			}
		}
		
		return classTable;
	}


	public Boolean run(boolean isUp, String className, List<String> tags) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		@SuppressWarnings("rawtypes")
		Class migrationClass = Class.forName(className);
		
		Migration migration = (Migration) migrationClass.newInstance();
		migration.setUnitTestDBIdentifier(unitTestDBIdentifier);
		
		return migration.run(isUp, className, tags);
	}


	public boolean scanAndRunByTags(boolean isUp, List<String> tags) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Boolean retValue = true;
		
		Hashtable<Long, String> classTable = getMigrationClassListByTags(tags);
		List<Long> versionNumbers = new ArrayList<Long>();
		
		Enumeration<Long> enumeration = classTable.keys();
		while (enumeration.hasMoreElements()) {
			versionNumbers.add(enumeration.nextElement());
		}
		
		Collections.sort(versionNumbers);
		if (!isUp) {
			Collections.reverse(versionNumbers);
		}
		
		for(Long versionNumber : versionNumbers) {
			retValue = run(isUp, classTable.get(versionNumber), tags);
			if (!retValue) {
				break;
			}
		}
		
		return retValue;
	}

}
