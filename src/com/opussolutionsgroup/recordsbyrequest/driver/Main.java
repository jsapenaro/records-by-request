package com.opussolutionsgroup.recordsbyrequest.driver;

import com.opussolutionsgroup.recordsbyrequest.config.XMLConfig;
import com.opussolutionsgroup.recordsbyrequest.global.Global;

public class Main {

	public static void main(String[] args) {
		assignProjectConfig();
	//	assignColumnConfig();
	}

	private static void assignProjectConfig() {
		Global.projectConfig = new XMLConfig(Global.CONFIG_PATH.concat("project_config.xml"));
		Global.projectConfig.buildProperties();
	}

	private static void assignColumnConfig() {
		Global.columnConfig = new XMLConfig(Global.CONFIG_PATH.concat("column_config.xml"));
		Global.columnConfig.buildProperties();
	}

}
