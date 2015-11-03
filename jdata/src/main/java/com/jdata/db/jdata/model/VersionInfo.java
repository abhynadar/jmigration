package com.jdata.db.jdata.model;

import java.util.Date;

public class VersionInfo {
	public long getVersionInfoId() {
		return versionInfoId;
	}
	public void setVersionInfoId(long versionInfoId) {
		this.versionInfoId = versionInfoId;
	}
	public Date getVersionAppliedOn() {
		return versionAppliedOn;
	}
	public void setVersionAppliedOn(Date versionAppliedOn) {
		this.versionAppliedOn = versionAppliedOn;
	}
	public String getVersionFileName() {
		return versionFileName;
	}
	public void setVersionFileName(String versionFileName) {
		this.versionFileName = versionFileName;
	}
	public String getVersionDescription() {
		return versionDescription;
	}
	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}
	public String getVersionTagRunFor() {
		return versionTagRunFor;
	}
	public void setVersionTagRunFor(String versionTagRunFor) {
		this.versionTagRunFor = versionTagRunFor;
	}
	
	long versionInfoId;
	Date versionAppliedOn;
	String versionFileName;
	String versionDescription;
	String versionTagRunFor;
}
