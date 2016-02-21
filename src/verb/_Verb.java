package verb;

public abstract class _Verb implements _VerbInterface{
	
	String[] synonyms;
	
	public boolean checkSynonyms(String str){
		for (int i = 0; i < synonyms.length; i++){
			if (synonyms[i].equals(str)){
				return true;
			}
		}
		return false;
	}

}
