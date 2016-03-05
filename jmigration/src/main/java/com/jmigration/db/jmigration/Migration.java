package com.jmigration.db.jmigration;

import java.util.List;

import com.jdata.db.jdata.core.AbstractDao;
import com.jdata.db.jdata.dao.VersionInfoDao;
import com.jdata.db.jdata.model.VersionInfo;
import com.jmigration.db.jmigration.core.Version;

public abstract class Migration extends AbstractDao {
	
	Version version;
	
	public Migration() {
		super();
		this.version = migrationVersion();
	}

	JLogger logger = new JLogger();
	
	public final Boolean run(Boolean isUp, String className, List<String> tags) {
		
		Boolean returnValue = true;
		String sql = "";
		VersionInfoDao versionInfoDao = null;
		
		try {

			Long versionId = version.getVersion();
			versionInfoDao = new VersionInfoDao();
			versionInfoDao.setUnitTestDBIdentifier(this.getUnitTestDBIdentifier());
			System.out.println(this.getUnitTestDBIdentifier());
			
			sql = isUp ? up() : down();
			VersionInfo existingVersion = versionInfoDao.getVersionInfoById(versionId);
			if (isUp) {
				if (existingVersion != null) {
					if (existingVersion.getVersionFileName().equalsIgnoreCase(className)) {
						return true;
					}
					else {
						logger.infoLog("Duplicate version number %s for file - %s", versionId, className);
						throw new Exception(String.format("Duplicate version number %s for file - %s", versionId, className));
					}
				}
			} else {
				if (existingVersion != null) {
					if (!existingVersion.getVersionFileName().equalsIgnoreCase(className)) {
						logger.infoLog("Duplicate version number %s for file - %s", versionId, className);
						throw new Exception(String.format("Duplicate version number %s for file - %s", versionId, className));
					}
				} else {
					return true;
				}
			}
			
			
			logger.infoLog("Executing migration %s for tags %s - version number - %s and filename - %s", isUp, Utility.ToCsvString(tags), versionId, className);
			logger.infoLog(sql);
			
			if (!sql.isEmpty()) {
				VersionInfo versionInfo = new VersionInfo();
				versionInfo.setVersionInfoId(versionId);
				versionInfo.setVersionFileName(className);
				versionInfo.setVersionTagRunFor(Utility.ToCsvString(tags));
				
				versionInfoDao.beginTransaction();
				
				this.executeUpdate(sql);
				
				if (isUp) {
					versionInfoDao.createVersionInfo(versionInfo);
				}
				else {
					versionInfoDao.deleteVersionInfo(versionId);
				}
				versionInfoDao.commitTransaction();
			}
			
			logger.infoLog("Successfully executed migration with version number - %s", versionId);

		} catch(Exception exception) {
			if (versionInfoDao != null) {
				versionInfoDao.rollbackTransaction();
			}
			returnValue = false;
			exception.printStackTrace();
			logger.errorLog(exception, "Exception occured when trying to execute migration with version number %s in class %s and sql - %s.", version.getVersion(), getClass().getName(), sql);
		} finally {
			if (versionInfoDao != null) {
				versionInfoDao.close();
			}
		}
		
		return returnValue;
	}
	
	public abstract String up();

	public abstract String down();
	
	public abstract Version migrationVersion();
		
}
