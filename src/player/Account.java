package player;

import java.util.ArrayList;
import java.util.Random;

import com.Globals;

import utility.fileIO.FileUtility;

public class Account {

	private static final int INDEXVERSIONNUM = 0;
	private static final int INDEXNAME = 1;
	private static final int INDEXPASSWORD = 2;
	
	private String name;
	private String password;
	
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
			if (FileUtility.saveFile(Globals.ACCOUNTSLOCATION, loc, getSaveList(), true, true)){
				return "Account: " + name + " created with password: " + password;
			} else {
				return "There was a problem saving this account.";
			}
		}
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
			return true;
			
		} else {
			return false;
		}
	}
	
	public boolean init(String accname, String accpassword){
		return loadAccount(accname, accpassword);
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
