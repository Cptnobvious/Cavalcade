package player;

import java.util.ArrayList;
import java.util.Random;

import com.Globals;

import utility.fileIO.FileUtility;

public class Account {

	private String name;
	private String password;
	
	public Account(){
		//Does nothing;
	}
	
	public String generateAccount(String accname){
		
		this.name = accname;
		
		String loc = name + ".acc";
		
		if (FileUtility.fileExists(loc)){
			return "This account name already exists";
		} else {
			generatePassword();
			if (FileUtility.saveFile(Globals.ACCOUNTSLOCATION, loc, getSaveList(), true, true)){
				return "Account: " + name + " created with password: " + password;
			} else {
				return "There was a problem saving this account.";
			}
		}
		
		//return "Creation failed. Unknown error.";
	}
	
	public boolean init(String name, String password){
		return true;
	}
	
	private boolean generatePassword(){
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		
		String newpass = String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
		for (int i = 0; i < 5; i++){
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
		
		return str;
	}
	
}
