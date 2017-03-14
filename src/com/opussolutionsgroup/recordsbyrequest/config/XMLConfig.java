package com.opussolutionsgroup.recordsbyrequest.config;

import java.io.FileInputStream;

import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

/**
 * 
 * @author Jonathan Sapenaro
 *
 */
public class XMLConfig {

	private final String PATH_TO_FILE;
	
	private Properties properties;

	private Vector<String> keysVector;
	private Vector<String> valuesVector;

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
		buildVectors();
	}

	/**
	 * Helper method that puts the properties into a Vector (required for the JTable)
	 */
	private void buildVectors() {
		keysVector = new Vector<String>(properties.size());
		valuesVector = new Vector<String>(properties.size());

		for (int i = 0; i < properties.size(); i++) {
			keysVector.add(properties.keySet().toArray()[i].toString());
			valuesVector.add(properties.values().toArray()[i].toString());
		}

		// reverse the order of the Vector so that it is in chronological order
		Collections.reverse(keysVector);
		Collections.reverse(valuesVector);
	}
	
	/**
	 * Method used to retrieve a value associated with a key
	 * 
	 * @param key The key that is associated to the value
	 * @return The value associated with the key
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}
	
	/**
	 * Method used to retrieve a vector that contains configuration keys
	 * 
	 * @return Vector of configuration keys
	 */
	public Vector<String> getKeysVector() {
		return keysVector;
	}

	/**
	 * Method used to retrieve a vector that contains configuration values
	 * 
	 * @return Vector of configuration values
	 */
	public Vector<String> getValuesVector() {
		return valuesVector;
	}
}
