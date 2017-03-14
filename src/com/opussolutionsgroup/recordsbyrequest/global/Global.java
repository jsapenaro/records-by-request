package com.opussolutionsgroup.recordsbyrequest.global;

import com.opussolutionsgroup.recordsbyrequest.config.XMLConfig;

public final class Global {
	
	
	public static final String RESOURCES_PATH = "resources/";
	public static final String CONFIG_PATH = RESOURCES_PATH.concat("config/");
	public static final String LIBRARY_PATH = RESOURCES_PATH.concat("libraries/");
	
	public static XMLConfig projectConfig;
	public static XMLConfig columnConfig;
	

}
