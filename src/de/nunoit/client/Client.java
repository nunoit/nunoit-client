package de.nunoit.client;

import java.util.UUID;

import de.nunoit.networking.protocol.Protocol;

public class Client {

	public static String CLIENT_UUID = UUID.randomUUID().toString();
	
	public static void main(String[] args) {
		Protocol.init();
		
		ServerConnection test = new ServerConnection("localhost", 7654);
		try {
			test.connect();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
