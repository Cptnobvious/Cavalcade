package clientHandling;

import java.net.Socket;
import java.util.ArrayList;

import com.VerboseDebug;

import clientHandling.Client;

public class ActiveClientsList {
	
	private static volatile ArrayList<Client> activeClients = new ArrayList<Client>();
	private static volatile ArrayList<ClientInput> pendingCommands = new ArrayList<ClientInput>();
	private static volatile ArrayList<ClientOutput> outputToClients = new ArrayList<ClientOutput>();

	public static boolean addClient(Socket socket){
		Client cli = new Client(socket, generateUniqueClientID());
		cli.init();
		activeClients.add(cli);
		return true;
	}
	
	public static boolean removeClient(int id){
		for (int i = 0; i < activeClients.size(); i++){
			if (activeClients.get(i).getUniqueID() == id){
				
				//TODO: when a client is removed clean up all pending commands
				activeClients.get(i).isDisconnecting();
				removeDisconnectedIO(activeClients.get(i).getUniqueID());
				activeClients.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	
	private static int generateUniqueClientID(){
		int id = 0;
		
		for (int i = 0; i < activeClients.size(); i++){
			if (id == activeClients.get(i).getUniqueID()){
				id++;
			} else {
				return id;
			}
		}
		
		return id;
	}
	
	public static boolean addPendingCommand(ClientInput cmd){
		pendingCommands.add(cmd);
		VerboseDebug.verboseDebugMessage("Client cmd added");
		return true;
	}
	
	public static boolean activateClients(){
		if (!activeClients.isEmpty()){
			for (int i = 0; i < activeClients.size(); i++){
				activeClients.get(i).addOutput("test");
				activeClients.get(i).active();
			}
		}
		return true;
	}
	
	public static boolean sendOutput(int id, String str){
		while (!outputToClients.isEmpty()){
			for(int i = 0; i < activeClients.size(); i ++){
				if (outputToClients.get(0).getID() == activeClients.get(i).getUniqueID()){
					activeClients.get(i).addOutput(outputToClients.get(0).getOutput());
				}
			}
			
			outputToClients.remove(0);
		}
		return true;
	}
	
	public static boolean runPlayerCommands(){
		//TODO: this block
		return false;
	}
	
	public static boolean removeDisconnectedIO(int uID){
		boolean isCleaned = false;
		int start = 0;
		
		while (!isCleaned){
			
			isCleaned = true;
			
			for (int i = start; i < pendingCommands.size(); i++){
				if (pendingCommands.get(i).getID() == uID){
					isCleaned = false;
					pendingCommands.remove(i);
					start = i - 1;
					break;
				}
			}
		}
		
		isCleaned = false;
		start = 0;
		
		while (!isCleaned){
			
			isCleaned = true;
			
			for (int i = start; i < outputToClients.size(); i++){
				if (outputToClients.get(i).getID() == uID){
					isCleaned = false;
					outputToClients.remove(i);
					start = i - 1;
					break;
				}
			}
		}
		
		return true;
	}
	
}
