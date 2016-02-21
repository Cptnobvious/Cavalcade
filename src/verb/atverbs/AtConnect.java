package verb.atverbs;

import player.Account;
import clientHandling.ActiveClientsList;
import logic.CommandHandler;
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
		boolean valid = acc.init(StringUtility.getWordInString(cmd, 2), StringUtility.getWordInString(cmd, 3));
		if (valid != true){
			ActiveClientsList.addOutputToClient(id, "Incorrect name or password");
		} else {
			ActiveClientsList.addOutputToClient(id, "Account loaded");
		}
	
		return false;
	}
	
	

}
