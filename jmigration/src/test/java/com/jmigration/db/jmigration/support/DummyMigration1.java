package com.jmigration.db.jmigration.support;

import com.jmigration.db.jmigration.Migration;
import com.jmigration.db.jmigration.core.Tags;
import com.jmigration.db.jmigration.core.Version;

@Tags(Value = "DEV,QA")
public class DummyMigration1 extends Migration {

	@Override
	public String up() {
		return "Alter TABLE VersionInfo MODIFY VersionDescription VARCHAR(500) NULL";
	}

	@Override
	public String down() {
		return "Alter TABLE VersionInfo MODIFY VersionDescription VARCHAR(500) NULL";
	}

	@Override
	public Version migrationVersion() {
		return Version.getDummyVersion(1);
	}

}
