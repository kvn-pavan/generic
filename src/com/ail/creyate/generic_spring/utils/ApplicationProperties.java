package com.ail.creyate.generic_spring.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
	private final static String file = "resources/application.properties";// Properties
																			// file

	private static Properties props = null;

	/**
	 * Find the properties file in the class path and load it.
	 * 
	 * @throws IOException
	 */
	private static void loadPropertiesFromClasspath() throws IOException {
		props = new Properties();
		InputStream inputStream = ApplicationProperties.class.getClassLoader()
				.getResourceAsStream(file);

		if (inputStream == null) {
			throw new FileNotFoundException("Property file '" + file
					+ "' not found in the classpath");
		}
		props.load(inputStream);
	}

	/**
	 * Look up a property from the properties file.
	 * 
	 * @param key
	 *            The name of the property to be found
	 * @return The value of the property
	 */
	public static String getProperty(String key) {
		if (props == null) {
			try {
				loadPropertiesFromClasspath();
			} catch (Exception IOException) {
				return null;
			}
		}
		return props.getProperty(key);
	}

	/**
	 * Look up a property from the properties file.
	 * 
	 * @param key
	 *            The name of the property to be found
	 * @return The value of the property
	 */
	public static String getProperty(String key, String defaultValue) {
		if (props == null) {
			try {
				loadPropertiesFromClasspath();
			} catch (Exception IOException) {
				return null;
			}
		}
		return props.getProperty(key, defaultValue);
	}

}
