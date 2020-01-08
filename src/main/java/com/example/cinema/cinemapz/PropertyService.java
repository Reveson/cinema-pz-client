package com.example.cinema.cinemapz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.example.cinema.cinemapz.utils.Constants;
import com.example.cinema.cinemapz.utils.StringUtils;

public class PropertyService {

	private static Properties properties = null;

	public static void initialize() throws IOException {
		initialize(null);
	}

	public static void initialize(String fileName) throws IOException {
		if (properties != null)
			throw new IllegalStateException("Properties file already initialized!");
		properties = new Properties();

		String fileNameToRead = StringUtils.isNullOrEmpty(fileName) ? Constants.DEFAULT_PROPERTIES_NAME : fileName;
		FileInputStream inputStream = new FileInputStream(Constants.PROPERIES_PATH + fileNameToRead);
		properties.load(inputStream);
		inputStream.close();
	}

	public static String getProperty(String key) {
		if(properties == null)
			throw new IllegalStateException("Properties file not yet initialized!");
		return properties.getProperty(key);
	}

}
