package verb.exclamverbs;

import clientHandling.ActiveClientsList;
import logic.CommandHandler;
import logic.PlayerList;
import utility.StringUtility;
import verb._Verb;

public class ExclamNewCharacter extends _Verb{

	public boolean setSynonyms() {
		String[] s = {"!NEWCHARACTER"};
		synonyms = s;
		return false;
	}

	public boolean init() {
		setSynonyms();
		CommandHandler.registerGlobalVerb(this);
		return true;
	}

	public boolean run(int id, String cmd) {
		String str = StringUtility.getStringAfterFirst(cmd);
		
		if (str != null && str.length() < 40){
			PlayerList.getAccountByID(id).newCharacter(str);
			ActiveClientsList.addOutputToClient(id, "Character created");
			PlayerList.getAccountByID(id).showCharacterList(id);
			return true;
		}
		return false;
	}

}
