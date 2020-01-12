package com.example.cinema.cinemapz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.example.cinema.cinemapz.error.ErrorCode;
import com.example.cinema.cinemapz.utils.Constants;
import com.example.cinema.cinemapz.utils.StringUtils;

public class PropertyService { //TODO singleton

    private static Properties properties = null;

    private static Map<String, String> errorCodeToPropertyString = Arrays.stream(ErrorCode.values())
            .collect(Collectors.toMap(ErrorCode::name,
                    errorCode -> "rest.error." + errorCode.name().replace("_", ".").toLowerCase()));

    private static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("messages", Constants.DEFAULT_LOCALE);

    public static void initialize() throws IOException {
        initialize(null);
    }

    public static void initialize(String fileName) throws IOException {
		if (properties != null) {
			throw new IllegalStateException("Properties file already initialized!");
		}
        properties = new Properties();

        String fileNameToRead =
                StringUtils.isNullOrEmpty(fileName) ? Constants.DEFAULT_PROPERTIES_NAME : fileName;
        FileInputStream inputStream = new FileInputStream(
                PropertyService.class.getClassLoader().getResource(fileNameToRead)
                        .getFile()); //TODO
        properties.load(inputStream);
        inputStream.close();
    }

    public static String getProperty(String key) {
		if (properties == null) {
			throw new IllegalStateException("Properties file not yet initialized!");
		}
        return properties.getProperty(key);
    }

    public static String getMessage(String property) {
        return resourceBundle.containsKey(property) ? resourceBundle.getString(property) : property;
    }

    public static void setLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    public static String mapErrorToProperty(String stringifiedErrorCode) {
        return errorCodeToPropertyString.getOrDefault(stringifiedErrorCode, stringifiedErrorCode);
    }

}
