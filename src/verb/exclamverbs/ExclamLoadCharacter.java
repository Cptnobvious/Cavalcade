package verb.exclamverbs;

import logic.CommandHandler;
import logic.PlayerList;
import utility.StringUtility;
import verb._Verb;

public class ExclamLoadCharacter extends _Verb{

	public boolean setSynonyms() {
		String[] s = {"!LOADCHARACTER"};
		synonyms = s;
		return true;
	}

	public boolean init() {
		setSynonyms();
		CommandHandler.registerGlobalVerb(this);
		return true;
	}

	public boolean run(int id, String cmd) {
		if (PlayerList.getAccountByID(id) != null){
			PlayerList.getAccountByID(id).setActiveCharacter(StringUtility.getStringAfterFirst(cmd));
			return true;
		}
		return false;
	}

}
