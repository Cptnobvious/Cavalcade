package clientHandling;

public class ClientOutput {

	int uID;
	String output;
	
	public ClientOutput(int uID, String output){
		this.uID = uID;
		this.output = output;
	}
	
	public int getID(){
		return uID;
	}
	
	public String getOutput(){
		return output;
	}
	
}
