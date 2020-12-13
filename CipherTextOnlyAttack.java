import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CipherTextOnlyAttack {

	public static int max3;
	public static String key3;

	// Main for testing 
	public static void main(String[] args) throws IOException {
		String plain_text = "masses on the spring, and they both take the same time to complete one oscillation, we can define them as having the same mass.\r\n"
				+ "Since I started this chapter by highlighting the relationship between conservation laws and symmetries, you're gone probably wondering cat elepent what symmetry is related to conservation of mass. at my home is dog I'll come back to that at the end of the chapter.\r\n"
				+ "When you learn about a new physical quantity, such as mass, you need to know what units are used to measure it. This will lead us to a brief digression on the metric system, after which we'll come back to physics.\r\n"
				+ "" ;
		String encrpty = CipherBlockChaining.encrypt(plain_text , "tyueawtmtq" , "dhagcfbe");
		String k =getKey(encrpty, "tyueawtmtq", "C:\\Users\\oraza\\Desktop\\CipherText_Key.txt");
		//System.out.println(k);
	}

	// Find the key of chiper
	public static String getKey(String chiper, String init_vector , String PathToTheFile) throws IOException {
		char [][] matrixChiper =  new char [2][26];
		char[] set1 = {'a', 'b','c','d','e','f','g','h'}; 
		int k = 8; 
		printAllKLength(set1, k ,chiper,init_vector,0);
		File CipherText_Key  = new File ("src/CipherText_Key.txt") ;
		FileWriter writer = new FileWriter(CipherText_Key , true);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		char abc = 'a';
		for (int i=0; i<key3.length(); i++) {
			bufferedWriter.write(abc + " "+key3.charAt(i));
			bufferedWriter.newLine();
			abc++;
		}
		bufferedWriter.close();
		return key3;
	}

	// Search words in descrypt text
	public static int searchWords(String plain_text, int max) {
		int counterword=0;
		String [] words = {"and", "plus", "furthermore", "moreover", "in addition", "also", "as well as", "when", "while", "as", "as soon as" ,"then", "after", "afterwards", "next", "firstly", "secondly", "finally", "but", "however", "though", "although", "nevertheless", "despite", "whereas", "as long as", "provided that", "unless", "otherwise", "because", "due to", "so", "in order to", "therefore", "as a result", "consequently",  "because of this", "before", "besides", "first", "for example", "furthermore", "in contrast", "in the end", "now", "Or", "since", "soon", "what", "yet", "was", "a" ,"in"  , "were", "his", "to", "an", "the", "at", "one" ,"through" ,"of" ,"thought", "not", "from",
				"with", "him", "on", "they", "we", "can", "will" , "new", "about", "used", "are", "know", "is", "as", "us", "need",
				"my" ,"where" ,"I", "my" ,"And", "Plus", "Furthermore", "Moreover", "In addition", "Also", "As well as", "When", "While", "As", "As soon as" ,"Then", "After", "Afterwards", "Next", "Firstly", "Secondly", "Finally", "But", "However", "Though", "Although", "Nevertheless", "Despite", "Whereas", "As long as", "Provided that", "Unless", "Otherwise", "Because" , "Due to", "So", "In order to", "Therefore", "As a result", "Consequently", "Because of this", "Before", "Besides", "First", "For example", "Furthermore", "In contrast", "In the end", "Last", "Now", "Or", "Since", "soon", "What", "Yet", "Was","In"  , "Were", "His", "To", "An", "The", "At", "One" ,"through" ,"Of" ,"Thought", "Not", "From",
				"With", "Him", "On", "They", "We", "Can", "Will" ,"New", "About", "Used", "Are", "Know", "Is", "As", "Us", "Need", "Where" ,"I"};;

		for(int i=0 ;i<words.length;i++) {
			// split the string by spaces in a 
			String a[] = plain_text.split(" "); 

			// search for pattern in a 
			for (int j = 0; j < a.length; j++)  
			{ 
				// if match found increase count 
				if (words[i].equals(a[j])) 
					counterword++; 
			} 

		}
		if(counterword>max) {

			return counterword;
		}
		return max;
	}

	// Check all possible keys  
	public static void printAllKLength(char[] set, int k, String chiper, String init_vector,int max) { 
		int n = set.length;  
		printAllKLengthRec(set, "", n, k,chiper, init_vector, max, ""); 
	} 

	// Check specific key and check if he is the best choice
	public static void printAllKLengthRec(char[] set,  String prefix, int n, int k, String chiper, String init_vector,int max,String key) { 
		// Base case: k is 0, 
		if (k == 0)  
		{   
			if(duplicate(prefix)==false) {
				String plaintext=CipherBlockChaining.decrypt (chiper,  init_vector,prefix);
				int ans =searchWords(plaintext,max);
				if(ans>max3) {
					max3=ans;
					key3=prefix;

				} 
			}
			return ; 
		}
		// One by one add all characters  
		// from set and recursively  
		// call for k equals to k-1 
		for (int i = 0; i < n; ++i) 
		{ 
			// Next character of input added 
			String newPrefix = prefix + set[i];  

			// k is decreased, because  
			// we have added a new character 
			printAllKLengthRec(set, newPrefix,  n, k - 1,chiper,  init_vector, max, key); 
		}
	}

	// Check if in String hase 2 same char
	public static boolean duplicate(String str) {
		for (int i=0; i<str.length();i++) {
			char letter = str.charAt(i);
			for(int j=i+1 ; j<str.length();j++) {
				if(letter == str.charAt(j)) {
					return true;
				}
			}
		}
		return false;
	}

}
