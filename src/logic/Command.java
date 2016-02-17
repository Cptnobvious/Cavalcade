package logic;

import clientHandling.ClientInput;

public class Command {
	
	//Client who issued this command
	private int uID;
	
	//String of command
	private String cmd;
	
	public Command(ClientInput input){
		this.uID = input.getID();
		this.cmd = input.getCommand();
	}
	
	public int getID(){
		return uID;
	}
	
	public String getFullText(){
		return cmd;
	}

}
