package verb.exclamverbs;

import logic.CommandHandler;
import logic.PlayerList;
import utility.StringUtility;
import verb._Verb;

public class ExclamDeleteCharacter extends _Verb{

	public boolean setSynonyms() {
		String[] s = {"!DELETECHARACTER"};
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
			String str = StringUtility.getStringAfterFirst(cmd);
			PlayerList.getAccountByID(id).deleteCharacter(str);
			return true;
		}
		return false;
	}

}
