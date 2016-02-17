package logic;

import java.util.ArrayList;

import clientHandling.ClientOutput;

//This handles incoming text from clients and uses it to make something happen

public class CommandHandler {

	private static ArrayList<Command> commands = new ArrayList<Command>();
	
	
	
	public static boolean addCommand(Command cmd){
		commands.add(cmd);
		return true;
	}
	
}
