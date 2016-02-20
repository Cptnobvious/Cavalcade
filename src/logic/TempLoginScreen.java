package logic;

import clientHandling.ActiveClientsList;

public class TempLoginScreen {

	public static void showScreen(int id){
		out(id, "Eight, sir; Seven, sir;");
		out(id, "Six, sir; Five, sir");
		out(id, "Four, sir; Three, sir;");
		out(id, "Two, sir; One!");
		out(id, "Tenser, said the Tensor.");
		out(id, "Tenser, said the Tensor.");
		out(id, "Tension, apprehension,");
		out(id, "And dissension have begun.");
		out(id, "      - The Demolished Man");
		out(id, "");
		out(id, "");
		out(id, "Type @CONNECT <account> <password> to login.");
		out(id, "Type @CREATE <account> to create a new account with that name.");
		out(id, "Type @DISCONNECT to disconnect.");
	}
	
	private static void out(int id, String str){
		ActiveClientsList.addOutputToClient(id, str);
	}
	
}
