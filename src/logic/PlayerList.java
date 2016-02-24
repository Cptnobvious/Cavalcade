package logic;

import java.util.ArrayList;

import player.Account;

public class PlayerList {

	static ArrayList<Account> accounts = new ArrayList<Account>();
	
	public static boolean addAccount(Account acc){
		accounts.add(acc);
		System.out.println("Account: " + acc.getName() + " has been loaded to ID " + acc.getClientID());
		return true;
	}
	
	public static boolean isAccountLoaded(Account acc){
		for (int i = 0; i < accounts.size(); i++){
			if (accounts.get(i).getName().equals(acc.getName())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean removeConnectedAccount(String name){
		for (int i = 0; i < accounts.size(); i++){
			if (accounts.get(i).getName().equals(name)){
				removeAccount(i);
				return true;
			}
		}
		return false;
	}
	
	public static boolean removeConnectedAccount(int id){
		for (int i = 0; i < accounts.size(); i++){
			if (accounts.get(i).getClientID() == id){
				removeAccount(i);
				return true;
			}
		}
		return false;
	}
	
	private static boolean removeAccount(int i){
		Account acc = accounts.get(i);
		acc.saveAccount();
		System.out.println("Logging out account " + acc.getName() + " at ID " + acc.getClientID() );
		accounts.remove(i);
		return true;
	}
	
	public static Account getAccountByID(int id){
		for (int i = 0; i < accounts.size(); i++){
			if (accounts.get(i).getClientID() == id){
				return accounts.get(i);
			}
		}
		
		return null;
	}
	
}
