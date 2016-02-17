package clientHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

//----------------------------------------------------------
//This class handles the individual clients input and output
//----------------------------------------------------------

public class Client extends Thread {

	private Socket cSocket;
	private BufferedReader in;
	private PrintWriter out;
	private volatile ClientInputWatcher inputWatcher;
	
	//This holds the output to the client
	private ArrayList<String> toOutput = new ArrayList<String>();
	
	//This is the unique client ID so messages can be sent to and received from this client
	private int uID;
	
	public Client(Socket socket, int ID){
		this.cSocket = socket;
		this.uID = ID;
	}
	
	public void init(){
		
		try {
			System.out.println("Client connected from: " + cSocket.getInetAddress() + " is now ID " + uID);
			
			
			in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			this.inputWatcher = new ClientInputWatcher(in);
			inputWatcher.start();
			out = new PrintWriter(cSocket.getOutputStream(), true);
			
			active();
			
		} catch (IOException e){
			e.printStackTrace();
		} 
		
	}
	
	public void active(){
		
		String input = null;
		
		
		if (!out.checkError()){
				
			if (inputWatcher.hasCommand()){
				//TODO: better way of handling "queue"
				input = inputWatcher.getEscalatedCommand();
				ActiveClientsList.addPendingCommand(new ClientInput(uID, input));
				System.out.println("Added cmd: " + input);
					
			}
				
			showOutput();
		} else {
			ActiveClientsList.removeClient(uID);
		}
		
		
	}
	
	public void showOutput(){
		while (!toOutput.isEmpty()){
			out.println(toOutput.get(0));
			toOutput.remove(0);
		}
	}
	
	public boolean addOutput(String str){
		toOutput.add(str);
		return true;
	}
	
	public void isDisconnecting(){
		closeConnection();
		System.out.println("Client disconnected from: " + cSocket.getInetAddress() + " is now ID " + uID);
	}
	
	private void closeConnection(){
		inputWatcher.kill();
		
		try {
			cSocket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getUniqueID(){
		return this.uID;
	}
	
}
