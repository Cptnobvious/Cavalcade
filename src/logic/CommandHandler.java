package logic;

import java.util.ArrayList;

import player.Account;
import utility.StringUtility;
import verb._Verb;
import verb.atverbs.AtConnect;
import verb.atverbs.AtCreate;
import verb.atverbs.AtDisconnect;
import verb.exclamverbs.ExclamDeleteCharacter;
import verb.exclamverbs.ExclamLoadCharacter;
import verb.exclamverbs.ExclamNewCharacter;
import verb.exclamverbs.ExclamShowCharacters;
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
			
			boolean found = false;
			
			for(int i = 0; i < globalVerbs.size(); i++){
				if (globalVerbs.get(i).checkSynonyms(first)){
					globalVerbs.get(i).run(id, cmd);
					commands.remove(0);
					found = true;
					break;
				}
			}
			
			if (!found){
				ActiveClientsList.addOutputToClient(id, "Command not understood");
				commands.remove(0);
			}
		}
		return true;
	}
	
	public static boolean initGlobalVerbs(){
		AtDisconnect atDisconnect = new AtDisconnect();
		atDisconnect.init();
		AtCreate atCreate = new AtCreate();
		atCreate.init();
		AtConnect atConnect = new AtConnect();
		atConnect.init();
		
		ExclamNewCharacter exnewchar = new ExclamNewCharacter();
		exnewchar.init();
		ExclamDeleteCharacter exdeletechar = new ExclamDeleteCharacter();
		exdeletechar.init();
		ExclamShowCharacters exshowchars = new ExclamShowCharacters();
		exshowchars.init();
		ExclamLoadCharacter exloadchar = new ExclamLoadCharacter();
		exloadchar.init();
		return true;
	}
	
	public static boolean registerGlobalVerb(_Verb verb){
		globalVerbs.add(verb);
		return true;
	}
}
