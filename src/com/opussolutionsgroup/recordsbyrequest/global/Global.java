package com.opussolutionsgroup.recordsbyrequest.global;

import java.util.ArrayList;

import com.opussolutionsgroup.recordsbyrequest.config.XMLConfig;
import com.opussolutionsgroup.recordsbyrequest.connection.MySQLConnection;
import com.opussolutionsgroup.recordsbyrequest.data.Row;

public final class Global {
	
	
	public static final String RESOURCES_PATH = "resources/";
	public static final String CONFIG_PATH = RESOURCES_PATH.concat("config/");
	public static final String LIBRARY_PATH = RESOURCES_PATH.concat("libraries/");
	public static final String IMAGE_PATH = RESOURCES_PATH.concat("images/");
	
	public static XMLConfig projectConfig;
	public static XMLConfig requestsColumnConfig;
	
	public static MySQLConnection connection;	
	
	public static ArrayList<Row> displayedRows = new ArrayList<Row>();

	//key values, not displayed values
	public static String[] visibleColumnNames;
	
	public static String requestTable;
	public static String manifestTable;

}
