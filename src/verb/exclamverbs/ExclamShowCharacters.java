package verb.exclamverbs;

import logic.CommandHandler;
import logic.PlayerList;
import verb._Verb;

public class ExclamShowCharacters extends _Verb{

	public boolean setSynonyms() {
		String[] s = {"!SHOWCHARACTERS"};
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
			PlayerList.getAccountByID(id).showCharacterList();
			return true;
		}
		
		return false;
	}

}
