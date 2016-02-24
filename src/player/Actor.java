package player;

import java.util.ArrayList;

import com.Globals;

public class Actor {

	private String accountOwner;
	private String name;
	private String description;
	
	public Actor(){
		
	}
	
	public Actor(String accown, String name, String description){
		this.accountOwner = accown;
		this.name = name;
		this.description = description;
	}
	
	public boolean newCharacter(String accown, String name){
		this.accountOwner = accown;
		this.name = name;
		this.description = "No Description Set";
		return true;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean setName(String name){
		this.name = name;
		return true;
	}
	
	public String getDesc(){
		return description;
	}
	
	public boolean setDescription(String desc){
		this.description = desc;
		return true;
	}
	
	public String getAccountOwner(){
		return accountOwner;
	}
	
	public Actor loadActor(int number, ArrayList<String> info){
		int start = 0;
		int place = number;
		for (int i = 0; i < info.size(); i++){
			if (info.get(i).equals("#BEGIN_ACTOR#")){
				place--;
				if (place == 0){
					start = i;
					break;
				}
			}
		}
		
		this.accountOwner = info.get(start);
		this.name = info.get(start + 1);
		this.description = info.get(start + 2);
		
		return null;
	}
	
	public ArrayList<String> getSaveList(){
		ArrayList<String> str = new ArrayList<String>();
		
		str.add("#BEGIN_ACTOR#");
		str.add(Globals.VERSION);
		str.add(accountOwner);
		str.add(name);
		str.add(description);
		str.add("#END_ACTOR#");
		
		return str;
	}
	
}
