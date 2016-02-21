package logic;

import java.util.ArrayList;

import player.Account;
import utility.StringUtility;
import verb.AtDisconnect;
import verb._Verb;
import clientHandling.ActiveClientsList;

//This handles incoming text from clients and uses it to make something happen

public class CommandHandler {

	private static ArrayList<Command> commands = new ArrayList<Command>();
	private static ArrayList<_Verb> globalVerbs = new ArrayList<_Verb>();
	
	public static boolean addCommand(Command cmd){
		commands.add(cmd);
		return true;
	}
	
	public static boolean processCommands(){
		while (!commands.isEmpty()){
			String cmd = commands.get(0).getFullText();
			String first = StringUtility.getFirstWord(cmd);
			int id = commands.get(0).getID();
			
			for(int i = 0; i < globalVerbs.size(); i++){
				if (globalVerbs.get(i).checkSynonyms(first)){
					globalVerbs.get(i).run(id, cmd);
					return true;
				}
			}
			
			if (first.equals("@CREATE")){
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
	
	public static boolean initGlobalVerbs(){
		AtDisconnect atDisconnect = new AtDisconnect();
		atDisconnect.init();
		return true;
	}
	
	public static boolean registerGlobalVerb(_Verb verb){
		globalVerbs.add(verb);
		return true;
	}
}
