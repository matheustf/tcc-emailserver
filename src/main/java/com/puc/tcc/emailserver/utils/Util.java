package com.puc.tcc.emailserver.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util {
	
	public static String readEmail(String name) {
		try {
			String fileName = "email/" + name;
			ClassLoader classLoader = new Util().getClass().getClassLoader();
			InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
			String contentFile = new BufferedReader(new InputStreamReader(resourceAsStream)).lines()
					.collect(Collectors.joining("\n"));
			return contentFile;
		} catch (Exception e) {
			System.out.println("Error file email");
			return null;
		}
	}


}
