package clientHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

//This is a class that watches for incoming input from a specific socket for the Client class

public class ClientInputWatcher extends Thread{

	private BufferedReader in;
	private volatile boolean clientAlive = true;
	private ArrayList<String> escalatedCommands = new ArrayList<String>();
	
	public ClientInputWatcher(BufferedReader in){
		this.in = in;
	}
	
	public void run(){
		String input;
		
		try {
			while (clientAlive){
				input = in.readLine();
				if (input != null){
					escalateCommand(input);
					System.out.println("Non null input");
				}
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			clientAlive = false;
		}
		
		
	}
	
	public void kill(){
		clientAlive = false;
	}
	
	private void escalateCommand(String str){
		escalatedCommands.add(str);
	}
	
	public boolean hasCommand(){
		return !escalatedCommands.isEmpty();
	}
	
	public String getEscalatedCommand(){
		String str = escalatedCommands.get(0);
		escalatedCommands.remove(0);
		return str;
	}
	
}
