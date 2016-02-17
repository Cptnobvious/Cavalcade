package utility.fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileUtility {
	
	public static String loadFile(String loc){
		String str = "0";
		
		File file = new File(loc);
		if (!file.exists()) {return null;}
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(loc));
			
			String line = in.readLine();
			str = line;
			
			while (true){
				line = in.readLine();
				if (line == null){
					break;
				}
				str = str + line;
			} 

			
			in.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: could not load file: " + loc);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		return str;
	}
	
	public static boolean saveFile(String loc, ArrayList<String> str){
		return saveFile(loc, str, false, true);
	}
	
	public static boolean saveFile(String loc, ArrayList<String> str, boolean create, boolean overwrite){
		try {
			
			File file = new File(loc);
			
			if (create && !file.exists()){
				file.createNewFile();
			}
			
			PrintWriter out = new PrintWriter(new FileOutputStream(loc, !overwrite));
			
			for (int i = 0; i < str.size(); i++){
				out.println(str.get(i));
			}
			
			out.flush();
			out.close();
			
		} catch (FileNotFoundException e){
			System.out.println("File does not exist: " + loc);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Could not save file:" + loc);
			e.printStackTrace();
			return false;
		}	

		return true;
	}
	
}
