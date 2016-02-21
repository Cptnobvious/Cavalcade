package verb;

import clientHandling.ActiveClientsList;
import logic.CommandHandler;

public class AtDisconnect extends _Verb{
	
	public boolean init(){
		setSynonyms();
		CommandHandler.registerGlobalVerb(this);
		return true;
	}

	public boolean setSynonyms() {
		String[] s = {"@DISCONNECT"};
		synonyms = s;
		return true;
	}

	public boolean run(int id, String cmd) {
		ActiveClientsList.queueRemoval(id);
		return false;
	}
	
}
