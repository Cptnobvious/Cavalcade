package com;

import logic.CommandHandler;
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
		
		CommandHandler.initGlobalVerbs();
		
		while (!isCloseRequested){
			ActiveClientsList.activateClients();
			CommandHandler.processCommands();
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
