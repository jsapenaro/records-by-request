package com.opussolutionsgroup.recordsbyrequest.config;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 
 * @author Jonathan Sapenaro
 *
 */
public class XMLConfig {

	private final String PATH_TO_FILE;

	private Properties properties;

	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();

	public XMLConfig(final String PATH_TO_FILE) {
		this.PATH_TO_FILE = PATH_TO_FILE;
	}

	/**
	 * Method that retrieves properties from external XML file and builds fields
	 */
	public void buildProperties() {

		properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream(PATH_TO_FILE));
		} catch (Exception e) {
			System.err.printf("Error reading from configuration file: %s.", e);
			System.exit(1);
		}
		buildArrayLists();
	}

	/**
	 * Helper method that puts the properties into an ArrayList
	 */
	private void buildArrayLists() {
		for (int i = 0; i < properties.size(); i++) {
			keys.add(properties.keySet().toArray()[i].toString());
			values.add(properties.values().toArray()[i].toString());
		}
	}

	/**
	 * Method used to retrieve a value associated with a key
	 * 
	 * @param key
	 *            The key that is associated to the value
	 * @return The value associated with the key
	 */
	public String getValue(String key) {
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		} else {
			System.err.printf("Error retrieving property: %s%n", key);
			return null;
		}
	}

	/**
	 * Method used to retrieve an ArrayList that contains configuration keys
	 * 
	 * @return ArrayList of configuration keys
	 */
	public ArrayList<String> getKeys() {
		return keys;
	}

	/**
	 * Method used to retrieve an ArrayList that contains configuration values
	 * 
	 * @return ArrayList of configuration values
	 */
	public ArrayList<String> getValues() {
		return values;
	}
}