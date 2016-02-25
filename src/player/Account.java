package player;

import java.util.ArrayList;
import java.util.Random;

import clientHandling.ActiveClientsList;

import com.Globals;

import utility.fileIO.FileUtility;

public class Account {

	private static final int INDEXVERSIONNUM = 0;
	private static final int INDEXNAME = 1;
	private static final int INDEXPASSWORD = 2;
	private static final int INDEXCHARACTERNUMBER = 3;
	
	private String name;
	private String password;
	
	private int linkedClientID;
	
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	private int activeCharacter = -1;
	
	public Account(){
		//Does nothing;
	}
	
	public String generateAccount(String accname){
		
		this.name = accname;
		generatePassword();
		
		String loc = name + ".acc";
		
		if (FileUtility.fileExists(loc)){
			return "This account name already exists";
		} else {
			if (saveAccount()){
				return "Account: " + name + " created with password: " + password;
			} else {
				return "There was a problem saving this account.";
			}
		}
	}
	
	public boolean saveAccount(){
		String loc = name + ".acc";
		return FileUtility.saveFile(Globals.ACCOUNTSLOCATION, loc, getSaveList(), true, true);
	}
	
	public boolean loadAccount(String accname, String accpassword){
		this.name = accname;
		
		String loc = Globals.ACCOUNTSLOCATION + name + ".acc";
		 
		if (FileUtility.fileExists(loc)){
			ArrayList<String> accinfo = new ArrayList<String>();
			accinfo = FileUtility.loadFile(loc);
			
			System.out.println(accpassword + "     " + accinfo.get(Account.INDEXPASSWORD));
			
			if (!accinfo.get(Account.INDEXPASSWORD).equals(accpassword)){
				return false;
			}
			
			this.name = accinfo.get(Account.INDEXNAME);
			this.password = accinfo.get(Account.INDEXPASSWORD);
			
			int totalCharacters = Integer.valueOf(accinfo.get(Account.INDEXCHARACTERNUMBER));
			
			for (int i = 0; i < totalCharacters; i++){
				Actor acc = new Actor();
				acc.loadActor(i, accinfo);
				actors.add(acc);
			}
			
			return true;
			
		} else {
			return false;
		}
	}
	
	public boolean init(String accname, String accpassword, int id){
		if (loadAccount(accname, accpassword)){
			this.linkedClientID = id;
			return true;
		} else {
			return false;
		}
	}
	
	private boolean generatePassword(){
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		
		String newpass = String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
		for (int i = 0; i < 4; i++){
			newpass = newpass + alphabet.charAt(r.nextInt(alphabet.length()));
		}
		
		password = newpass;
		
		return true;
	}
	
	private ArrayList<String> getSaveList(){
		ArrayList<String> str = new ArrayList<String>();
		str.add(Globals.VERSION);
		str.add(name);
		str.add(password);
		
		
		str.add(String.valueOf(actors.size()));
		for (int i = 0; i < actors.size(); i++){
			ArrayList<String> charinfo = actors.get(i).getSaveList();
			for (int j = 0; j < charinfo.size(); j++){
				str.add(charinfo.get(j));
			}
		}
		
		
		return str;
	}
	
	public String getName(){
		return name;
	}
	
	public int getClientID(){
		return linkedClientID;
	}
	
	public boolean showCharacterList(){
		for (int i = 0; i < actors.size(); i++){
			ActiveClientsList.addOutputToClient(linkedClientID, i + ": " + actors.get(i).getName());
		}
		return true;
	}
	
	public boolean deleteCharacter(String name){
		for (int i = 0; i < actors.size(); i++){
			if (actors.get(i).getName().contains(name)){
				ActiveClientsList.addOutputToClient(linkedClientID, actors.get(i).getName() + " deleted!");
				actors.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean newCharacter(String name){
		Actor act = new Actor();
		act.newCharacter(this.name, name);
		actors.add(act);
		return true;
	}
	
	public boolean setActiveCharacter(int index){
		if (index > actors.size()){
			return false;
		}
		
		activeCharacter = index;
		ActiveClientsList.addOutputToClient(linkedClientID, actors.get(index).getName() + " loaded!");
		return true;
	}
	
	public boolean setActiveCharacter(String name){
		for (int i = 0; i < actors.size(); i++){
			if (actors.get(i).getName().contains(name)){
				return setActiveCharacter(i);
			}
		}
		return false;
	}
	
	public Actor getActiveCharacter(){
		return actors.get(activeCharacter);
	}
	
}
