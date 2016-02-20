package clientHandling;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import logic.Command;
import logic.CommandHandler;
import logic.TempLoginScreen;
import clientHandling.Client;

public class ActiveClientsList {
	
	private static volatile ArrayList<Client> activeClients = new ArrayList<Client>();
	private static volatile ArrayList<ClientInput> pendingCommands = new ArrayList<ClientInput>();
	private static volatile ArrayList<ClientOutput> outputToClients = new ArrayList<ClientOutput>();
	private static volatile ArrayList<Integer> queuedRemovals = new ArrayList<Integer>();
	private static volatile ArrayList<Socket> queuedClients = new ArrayList<Socket>();

	private static boolean addClient(Socket socket){
		Client cli = new Client(socket, generateUniqueClientID());
		if (cli.init()){
			activeClients.add(cli);
			//TODO: better system than this for login screen. Tie to event system
			TempLoginScreen.showScreen(cli.getUniqueID());
		}
		return true;
	}
	
	public static boolean queueClientAddition(Socket socket){
		queuedClients.add(socket);
		return true;
	}
	
	private static boolean addQueuedClients(){
		while (!queuedClients.isEmpty()){
			addClient(queuedClients.get(0));
			queuedClients.remove(0);
		}
		
		return true;
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
		return true;
	}
	
	public static boolean activateClients(){
		removeQueued();
		
		sendOutput();
		runPlayerCommands();
		
		if (!activeClients.isEmpty()){
			for (int i = 0; i < activeClients.size(); i++){
				try {
					activeClients.get(i).active();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		addQueuedClients();
		
		return true;
	}
	
	
	public static boolean addOutputToClient(int id, String str){
		ClientOutput out = new ClientOutput(id, str);
		outputToClients.add(out);
		return true;
	}
	
	private static boolean sendOutput(){
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
		while (!pendingCommands.isEmpty()){
			CommandHandler.addCommand(new Command(pendingCommands.get(0)));
			pendingCommands.remove(0);
		}
		return true;
	}
	
	public static boolean queueRemoval(int id){
		queuedRemovals.add(id);
		return true;
	}
	
	
	public static boolean removeQueued(){
		while (!queuedRemovals.isEmpty()){
			removeClient(queuedRemovals.get(0));
			queuedRemovals.remove(0);
		}
		
		return true;
	}
	
	private static boolean removeClient(int id){
		for (int i = 0; i < activeClients.size(); i++){
			if (activeClients.get(i).getUniqueID() == id){
				activeClients.get(i).isDisconnecting();
				removeDisconnectedIO(activeClients.get(i).getUniqueID());
				activeClients.remove(i);
				return true;
			}
		}
		
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
					start = i;// - 1;
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
					start = i;// - 1;
					break;
				}
			}
		}
		
		return true;
	}
	
}
