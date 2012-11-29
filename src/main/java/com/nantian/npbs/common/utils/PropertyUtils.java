package com.nantian.npbs.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * Properties Util函数.
 * 
 * @author Edmond Lee
 */
public class PropertyUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

	private static Properties props = null;
	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * 载入多个properties文件, 相同的属性最后载入的文件将会覆盖之前的载入.
	 * 
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... locations)
			throws IOException {
		synchronized (PropertyUtils.class) {
			if (props != null)
				return props;
			props = new Properties();
		}

		for (String location : locations) {
			logger.debug("Loading properties file from classpath:" + location);
			InputStream is = null;
			try {
				if (location.startsWith("classpath")) {
					Resource resource = resourceLoader.getResource(location);
					is = resource.getInputStream();
				} else {
					is = new FileInputStream(location);
				}
				propertiesPersister.load(props, new InputStreamReader(is,
						DEFAULT_ENCODING));
			} catch (IOException ex) {
				logger.error("Could not load properties from classpath:"
						+ location + ": " + ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}

	public static String getString(String propertyName) {
		return getString(propertyName, "");
	}

	/**
	 * Get a property value as a string.
	 * 
	 * @see #extractPropertyValue(String, java.util.Properties)
	 * 
	 * @param propertyName
	 *            The name of the property for which to retrieve value
	 * @param properties
	 *            The properties object
	 * @param defaultValue
	 *            The default property value to use.
	 * @return The property value; may be null.
	 */
	public static String getString(String propertyName, String defaultValue) {
		String value = extractPropertyValue(propertyName, props);
		return value == null ? defaultValue : value;
	}

	/**
	 * Extract a property value by name from the given properties object.
	 * <p/>
	 * Both <tt>null</tt> and <tt>empty string</tt> are viewed as the same, and
	 * return null.
	 * 
	 * @param propertyName
	 *            The name of the property for which to extract value
	 * @param properties
	 *            The properties object
	 * @return The property value; may be null.
	 */
	public static String extractPropertyValue(String propertyName,
			Properties properties) {
		String value = properties.getProperty(propertyName);
		if (value == null) {
			return null;
		}
		value = value.trim();
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return value;
	}

	/**
	 * Get a property value as a boolean. Shorthand for calling
	 * {@link #getBoolean(String, java.util.Properties, boolean)} with
	 * <tt>false</tt> as the default value.
	 * 
	 * @param propertyName
	 *            The name of the property for which to retrieve value
	 * @param properties
	 *            The properties object
	 * @return The property value.
	 */
	public static boolean getBoolean(String propertyName) {
		return getBoolean(propertyName, props, false);
	}

	/**
	 * Get a property value as a boolean.
	 * <p/>
	 * First, the string value is extracted, and then
	 * {@link Boolean#valueOf(String)} is used to determine the correct boolean
	 * value.
	 * 
	 * @see #extractPropertyValue(String, java.util.Properties)
	 * 
	 * @param propertyName
	 *            The name of the property for which to retrieve value
	 * @param properties
	 *            The properties object
	 * @param defaultValue
	 *            The default property value to use.
	 * @return The property value.
	 */
	public static boolean getBoolean(String propertyName,
			Properties properties, boolean defaultValue) {
		String value = extractPropertyValue(propertyName, properties);
		return value == null ? defaultValue : Boolean.valueOf(value)
				.booleanValue();
	}

	/**
	 * Get a property value as an int.
	 * <p/>
	 * First, the string value is extracted, and then
	 * {@link Integer#parseInt(String)} is used to determine the correct int
	 * value for any non-null property values.
	 * 
	 * @see #extractPropertyValue(String, java.util.Properties)
	 * 
	 * @param propertyName
	 *            The name of the property for which to retrieve value
	 * @param properties
	 *            The properties object
	 * @param defaultValue
	 *            The default property value to use.
	 * @return The property value.
	 */
	public static int getInt(String propertyName, int defaultValue) {
		String value = extractPropertyValue(propertyName, props);
		return value == null ? defaultValue : Integer.parseInt(value);
	}

	/**
	 * Get a property value as an Integer.
	 * <p/>
	 * First, the string value is extracted, and then
	 * {@link Integer#valueOf(String)} is used to determine the correct boolean
	 * value for any non-null property values.
	 * 
	 * @see #extractPropertyValue(String, java.util.Properties)
	 * 
	 * @param propertyName
	 *            The name of the property for which to retrieve value
	 * @param properties
	 *            The properties object
	 * @return The property value; may be null.
	 */
	public static Integer getInteger(String propertyName) {
		String value = extractPropertyValue(propertyName, props);
		return value == null ? null : Integer.valueOf(value);
	}

	public static String extractFromSystem(String systemPropertyName) {
		try {
			return System.getProperty(systemPropertyName);
		} catch (Throwable t) {
			return null;
		}
	}
}
