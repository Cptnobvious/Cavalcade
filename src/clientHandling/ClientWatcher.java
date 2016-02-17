package clientHandling;

import java.io.IOException;
import java.net.ServerSocket;

//--------------------------------------------------
//This class watches for incoming client connections
//--------------------------------------------------

public class ClientWatcher extends Thread {

	private final int PORT;
	private static ServerSocket incomingConnectionListener = null;
	private boolean isRunning = false;
	
	//Start the client watcher with given port number
	public ClientWatcher(int port){
		this.PORT = port;
	}
	
	//Start the client watcher
	public void run(){
		if (!isRunning) {
			try {
				incomingConnectionListener = new ServerSocket(PORT);
				System.out.println("Server is now listening for connections");
				
				while(true){
					ActiveClientsList.queueClientAddition(incomingConnectionListener.accept());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					incomingConnectionListener.close();
				} catch (IOException e ) {
					System.out.println("Error: Problem closing the incoming connection listener!");
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Error: Tried to start the client watcher more than once!");
		}
	}
		
	public void startListening(){
		//TODO: run should be doing something here for checking if it's running twice
	}
	
}
