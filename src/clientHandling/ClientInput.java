package clientHandling;

//This class holds the input object, linking a command with a client ID so we can tell who called it

public class ClientInput {

	private int id;
	private String command;
	
	public ClientInput(int uID, String command){
		this.id = uID;
		this.command = command;
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getCommand(){
		return this.command;
	}
	
}
