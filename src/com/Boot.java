package com;

import java.util.ArrayList;

import utility.fileIO.FileUtility;
import clientHandling.ActiveClientsList;
import clientHandling.ClientWatcher;

public class Boot {

	
	//TODO: Port defined by config
	private static final int PORT = 7777;
	private static ClientWatcher clientWatcher;
	private static boolean isCloseRequested = false;
	
	public static void main(String[] args) {
		
		clientWatcher = new ClientWatcher(PORT);
		clientWatcher.start();
		
		while (!isCloseRequested){
			ActiveClientsList.activateClients();
			ActiveClientsList.runPlayerCommands();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("test");
	}

}
