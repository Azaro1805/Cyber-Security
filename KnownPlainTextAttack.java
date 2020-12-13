import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KnownPlainTextAttack {

	public static int max3;
	public static String key3 ="abvcfabvcfabvcfabvcfabvcfabvcfabvcfabvcfabvcfabvcfds";
	public static String plain_text;

	// Main for testing 
	public static void main (String args [] ) throws IOException {
		File Iv_vector  = new File("C:\\Users\\oraza\\Desktop\\IVLong_Example.txt");
		File CipherTextLong_Example  = new File("C:\\Users\\oraza\\Desktop\\CipherTextLong_Example.txt");
		File PlainMessage_Example  = new File("C:\\Users\\oraza\\Desktop\\PlainMessage_Example.txt");
		File CipherMessage_Example  = new File("C:\\Users\\oraza\\Desktop\\CipherMessage_Example.txt");

		Known_Plain_Text(Iv_vector, CipherTextLong_Example, PlainMessage_Example, CipherMessage_Example);
	}

	// Read all the Files
	public static String Known_Plain_Text(File Iv_vector2, File Chiper_text2, File PlainMessage_Example, File CipherMessage_Example) throws IOException {

		String path = Iv_vector2.getAbsolutePath();
		String Iv_vector = Files.readString(Paths.get(path));
		String path2 = Chiper_text2.getAbsolutePath();
		String Chiper_text = Files.readString(Paths.get(path2));
		String path3 = PlainMessage_Example.getAbsolutePath();
		plain_text = Files.readString(Paths.get(path3));
		String path4 = CipherMessage_Example.getAbsolutePath();
		String Chiper_text_to_compare = Files.readString(Paths.get(path4));
		String k =getKey(Chiper_text_to_compare, Iv_vector);
		return null;
	}

	// Find the key of chiper
	public static String getKey(String chiper, String init_vector) throws IOException {
		char [][] matrixChiper =  new char [2][52];
		char[] set1 = {'a', 'b','c','d','e','f','g','h','i', 'j','k','l','m','n','o','p','q','r', 's','t','u','v','w','x','y','z',
				'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; 
		int k = 52; 
		printAllKLength(set1, k ,chiper,init_vector,0);

		File CipherText_Key  = new File ("src/PlainText_Key.txt") ;
		FileWriter writer = new FileWriter(CipherText_Key , true);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		char abc = 'a';
		for (int i=0; i<key3.length(); i++) {
			bufferedWriter.write(abc + " "+key3.charAt(i));
			bufferedWriter.newLine();
			abc++;
			if(i==25) {
				abc='A';
			}
		}
		bufferedWriter.close();
		return key3;
	}

	// Search words in descrypt text
	public static int searchWords(String plain_text2, int max) {
		int counterword=0;
		for(int i=0; i<plain_text.length();i++) {
			if ((Character.compare(plain_text.charAt(i), plain_text2.charAt(i)))==0) {
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
				String plaintext3=CipherBlockChaining.decrypt (chiper,init_vector,prefix);
				int ans =searchWords(plaintext3,max);
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
			String newPrefix = prefix + set[i];  
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