package verb.atverbs;

import player.Account;
import clientHandling.ActiveClientsList;
import logic.CommandHandler;
import utility.StringUtility;
import verb._Verb;

public class AtCreate extends _Verb{

	public boolean setSynonyms() {
		String[] s = {"@CREATE"};
		synonyms = s;
		return false;
	}

	public boolean init() {
		setSynonyms();
		CommandHandler.registerGlobalVerb(this);
		return true;
	}

	public boolean run(int id, String cmd) {
		String str = StringUtility.getWordInString(cmd, 2);
		
		if (str.length() < 32){
			Account acc = new Account();
			String result = acc.generateAccount(StringUtility.getWordInString(cmd, 2));
			ActiveClientsList.addOutputToClient(id, result);
			return true;
		}
		
		return false;
	}

}
