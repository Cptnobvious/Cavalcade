package utility;

public class StringUtility {

	public static String getFirstWord(String str){
		return getWordInString(str, 1);
	}
	
	public static String getWordInString(String str, int i){
		String[] arr = str.split(" ");
		if (arr.length >= i){
			return arr[i-1];
		} else {
			return null;
		}
	}
	
	public static String getStringAfterFirst(String str){
		String[] arr = str.split(" ");
		String result = null;
		for (int i = 1; i < arr.length; i ++){
			if (i == 1){result = arr[1];} else {result = result + arr[i];}
		}
		return result;
	}
}
