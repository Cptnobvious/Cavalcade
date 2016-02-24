package verb.atverbs;

import player.Account;
import clientHandling.ActiveClientsList;
import logic.CommandHandler;
import logic.PlayerList;
import utility.StringUtility;
import verb._Verb;

public class AtConnect extends _Verb{

	public boolean setSynonyms() {
		String[] s = {"@CONNECT"};
		synonyms = s;
		return false;
	}

	public boolean init() {
		setSynonyms();
		CommandHandler.registerGlobalVerb(this);
		return false;
	}

	public boolean run(int id, String cmd) {
		
		Account acc = new Account();
		boolean valid = acc.init(StringUtility.getWordInString(cmd, 2), StringUtility.getWordInString(cmd, 3), id);
		if (valid != true){
			ActiveClientsList.addOutputToClient(id, "Incorrect name or password");
		} else {
			if (PlayerList.isAccountLoaded(acc)){
				PlayerList.removeConnectedAccount(acc.getName());
			}
			PlayerList.addAccount(acc);
			ActiveClientsList.addOutputToClient(id, "Account loaded");
			ActiveClientsList.addOutputToClient(id, "--------------------");
			ActiveClientsList.addOutputToClient(id, "!NEWCHARACTER <name>");
			acc.showCharacterList(id);
		}
	
		return false;
	}
	
	

}
