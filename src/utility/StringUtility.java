package utility;

public class StringUtility {

	public static String getFirstWord(String str){
		return getWordInString(str, 1);
	}
	
	public static String getWordInString(String str, int i){
		String[] arr = str.split(" ");
		return arr[i-1];
	}
}
