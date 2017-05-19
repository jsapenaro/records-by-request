package com.opussolutionsgroup.recordsbyrequest.driver;

import com.opussolutionsgroup.recordsbyrequest.config.XMLConfig;
import com.opussolutionsgroup.recordsbyrequest.connection.MySQLConnection;
import com.opussolutionsgroup.recordsbyrequest.global.Global;

public class Main {

	/**
	 * Entry point to the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		assignProjectConfig();
		assignColumnConfig();
		assignVisibleColumnIndexes();
		assignTableNames();
		assignDatabaseConnection();

		Global.connection.connect();
		//String[] words = { "sapenaro", "jonathan" };
	//	Global.connection.executeQuery(Global.requestTable, "Select *", words);
		Global.connection.disconnect();

	}

	private static void assignProjectConfig() {
		Global.projectConfig = new XMLConfig(
				Global.CONFIG_PATH.concat("project_config.xml"));
		Global.projectConfig.buildProperties();
	}

	private static void assignColumnConfig() {
		Global.requestsColumnConfig = new XMLConfig(
				Global.CONFIG_PATH.concat("request_columns_config.xml"));
		Global.requestsColumnConfig.buildProperties();
	}

	private static void assignVisibleColumnIndexes() {
		Global.visibleColumnNames = Global.projectConfig.getValue(
				"VISIBLE_COLUMNS").split(":");
	}

	private static void assignTableNames() {
		Global.requestTable = Global.projectConfig.getValue("REQUESTS");
		Global.manifestTable = Global.projectConfig.getValue("MANIFEST");
	}

	private static void assignDatabaseConnection() {
		Global.connection = new MySQLConnection(
				Global.projectConfig.getValue("HOST"),
				Global.projectConfig.getValue("PORT"));
	}

}
