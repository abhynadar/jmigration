package com.jdata.db.jdata.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.jdata.db.jdata.core.AbstractDao;
import com.jdata.db.jdata.helpers.ConfigHelper;
import com.jdata.db.jdata.model.VersionInfo;

public class VersionInfoDao extends AbstractDao {
	private ConfigHelper config;
	
	private String tableName = "VersionInfo";
	
	public VersionInfoDao() {
		config = new ConfigHelper();
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public boolean isVersionInfoAvailable() throws SQLException {
		boolean retValue = false;
		ResultSet rs = null; 
		String schemaName = config.getDBName().toLowerCase() + this.getUnitTestDBIdentifier().toLowerCase();
		String sql = "SELECT count(*) AS rowCount FROM information_schema.tables WHERE table_schema = '" + schemaName + "' AND table_name = '" + this.getTableName() + "'";
		
		try {
			rs = this.executeQuery(sql);
			
			while (rs.next()) {
				if (rs.getInt("rowCount") > 0 ) {
					retValue = true;
					break;
				}
			}
		} finally {
			this.close(rs);
			this.close();
		}
		
		return retValue;
	}
	
	public void createVersionInfoTable() throws SQLException {
		
		String sql = "CREATE TABLE VersionInfo ( " +
					"VersionInfoId BIGINT NOT NULL, " +
					"VersionAppliedOn DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
					"VersionFileName VARCHAR(500) NOT NULL, " +
					"VersionDescription VARCHAR(500) NULL, " +
					"VersionTagRunFor VARCHAR(50) NOT NULL, " +
					"PRIMARY KEY (VersionInfoId) " +
					")";
		
		executeUpdate(sql);
		
	}
	
	public VersionInfo getVersionInfoById(long versionInfoId) throws SQLException {
		ResultSet rs = null;
		VersionInfo versionInfo = null;
				
		String sql = "Select * from VersionInfo Where VersionInfoId = ?";
		
		try {
			PreparedStatement prepStatement = this.getPreparedStatement(sql);
			prepStatement.setLong(1, versionInfoId);
			rs = prepStatement.executeQuery();
			
			while(rs.next()) {
				versionInfo = new VersionInfo();
				versionInfo.setVersionInfoId(rs.getLong("VersionInfoId"));
				versionInfo.setVersionAppliedOn(rs.getDate("VersionAppliedOn"));
				versionInfo.setVersionFileName(rs.getString("VersionFileName"));
				versionInfo.setVersionDescription(rs.getString("VersionDescription"));
				versionInfo.setVersionTagRunFor(rs.getString("VersionTagRunFor"));
				break;
			}
			
			if (versionInfo != null) {
				Long versionId = versionInfo.getVersionInfoId();
				if (versionId == 0 || versionId != versionInfoId) {
					versionInfo = null;
				}
			}
			
		} finally {
			this.close(rs);
			this.close();
		}
		
		return versionInfo;
	}
	
	
	public boolean createVersionInfo(VersionInfo versionInfo) throws SQLException {
		boolean result = false;		
		String sql = "INSERT into VersionInfo (VersionInfoId, VersionAppliedOn, VersionFileName, VersionDescription, VersionTagRunFor) " + 
						"VALUES (?, default, ?, ?, ?)";
		
		try {
			PreparedStatement prepStatement = this.getPreparedStatement(sql);
			
			prepStatement.setLong(1, versionInfo.getVersionInfoId());
			prepStatement.setString(2, versionInfo.getVersionFileName());
			prepStatement.setString(3, versionInfo.getVersionDescription());
			prepStatement.setString(4, versionInfo.getVersionTagRunFor());
			
			int returnValue = prepStatement.executeUpdate();
			if (returnValue > 0) {
				result = true;
			}
			
		} finally {
			this.close();
		}
		
		return result;
	}
	
	public boolean deleteVersionInfo(long versionInfoId) throws SQLException {
		boolean result = false;		
		String sql = "Delete from VersionInfo Where VersionInfoId = ?";
		
		try {
			PreparedStatement prepStatement = this.getPreparedStatement(sql);
			
			prepStatement.setLong(1, versionInfoId);
			
			int returnValue = prepStatement.executeUpdate();
			if (returnValue > 0) {
				result = true;
			}
			
		} finally {
			this.close();
		}
		
		return result;
	}
	
	
}
