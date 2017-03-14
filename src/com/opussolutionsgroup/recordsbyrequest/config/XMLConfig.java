package com.opussolutionsgroup.recordsbyrequest.config;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;

public class XMLConfig {

	private final String PATH_TO_FILE;

	private Vector<String> keysVector;
	private Vector<String> valuesVector;

	private Properties properties;

	public XMLConfig(final String PATH_TO_FILE) {
		this.PATH_TO_FILE = PATH_TO_FILE;
	}

	public void buildProperties() {

		properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream(PATH_TO_FILE));
		} catch (Exception e) {
			System.err.printf("Error reading from configuration file: %s.", e);
			System.exit(1);
		}
		keysVector = (Vector) properties.keySet();
		
		for(int i = 0; i < keysVector.size(); i++) {
			System.out.println(keysVector.get(i));
		}
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}

	public Vector<String> getKeysVector() {
		return null;
	}

	public Vector<String> getValuesVector() {
		return null;
	}
}
