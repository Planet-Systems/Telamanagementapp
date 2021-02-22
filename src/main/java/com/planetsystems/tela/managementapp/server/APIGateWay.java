package com.planetsystems.tela.managementapp.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class APIGateWay {

	private OutputStream outStream;
	private InputStream input = null;
	private Properties prop = new Properties();

	private static APIGateWay instance = new APIGateWay();

	private APIGateWay() {

	}

	public static APIGateWay getInstance() {
		return instance;
	}

	public String getApLink() {

		String apiLink = null;
		try {

			String filename = "properties.properties";

			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return null;
			}

			// load a properties file from class path, inside static method

			prop.load(input);

			apiLink = prop.getProperty("api");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return apiLink;
	}

	public Client getClientBuilder() {
		return ClientBuilder.newClient();
	}

}
