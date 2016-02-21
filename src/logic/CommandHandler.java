package logic;

import java.util.ArrayList;

import player.Account;
import utility.StringUtility;
import clientHandling.ActiveClientsList;

//This handles incoming text from clients and uses it to make something happen

public class CommandHandler {

	private static ArrayList<Command> commands = new ArrayList<Command>();
	
	public static boolean addCommand(Command cmd){
		commands.add(cmd);
		return true;
	}
	
	public static boolean processCommands(){
		while (!commands.isEmpty()){
			String cmd = commands.get(0).getFullText();
			String first = StringUtility.getFirstWord(cmd);
			int id = commands.get(0).getID();
			
			if (first.equals("@DISCONNECT")){
				ActiveClientsList.queueRemoval(id);
			} else if (first.equals("@CREATE")){
				Account acc = new Account();
				String result = acc.generateAccount(StringUtility.getStringAfterFirst(cmd));
				ActiveClientsList.addOutputToClient(id, result);
				//ActiveClientsList.queueRemoval(id);
			} else if (first.equals("@CONNECT")){
				Account acc = new Account();
				boolean valid = acc.init(StringUtility.getWordInString(cmd, 2), StringUtility.getWordInString(cmd, 3));
				if (valid != true){
					ActiveClientsList.addOutputToClient(id, "Incorrect name or password");
				} else {
					ActiveClientsList.addOutputToClient(id, "Account loaded");
				}
			}
			
			commands.remove(0);
		}
		return true;
	}
	
}
