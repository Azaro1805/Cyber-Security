public class Ex_1 {

	public static int max3;
	public static String key3;

	public static void main(String[] args) {
		part1A();

	}





	///// Part 1 \\\\\\

	public static void part1A () {
		System.out.println("start with : " + "");
		String ans = encrypt("It was a bright cold day in April, and the clocks were striking\r\n" + 
				"thirteen. Winston Smith, his chin nuzzled into his\r\n" + 
				"breast in an effort to escape the vile wind, slipped quickly\r\n" + 
				"through the glass doors of Victory Mansions, though not\r\n" + 
				"quickly enough to prevent a swirl of gritty dust from entering\r\n" + 
				"along with him.\r\n" + 
				"The hallway smelt of boiled cabbage and old rag mats. At\r\n" + 
				"one end of it a coloured poster, too large for indoor display,\r\n" + 
				"had been tacked to the wall. It depicted simply an enormous\r\n" + 
				"face, more than a metre wide: the face of a man of\r\n" + 
				"about forty-five, with a heavy black moustache and ruggedly\r\n" + 
				"handsome features. Winston made for the stairs. It was\r\n" + 
				"no use trying the lift. Even at the best of times it was seldom\r\n" + 
				"working, and at present the electric current was cut\r\n" + 
				"off during daylight hours. It was part of the economy drive\r\n" + 
				"in preparation for Hate Week. The flat was seven flights up,\r\n" + 
				"and Winston, who was thirty-nine and had a varicose ulcer\r\n" + 
				"above his right ankle, went slowly, resting several times on\r\n" + 
				"the way. On each landing, opposite the lift-shaft, the poster\r\n" + 
				"with the enormous face gazed from the wall. It was one of\r\n" + 
				"those pictures which are so contrived that the eyes follow\r\n" + 
				"you about when you move. BIG BROTHER IS WATCHING\r\n" + 
				"YOU, the caption beneath it ran.  " , "tyueawtmtq", "dhagcfbe");
		System.out.println("ans = "+ ans);
		String reverseAns = decrypt(ans,"tyueawtmtq", "dhagcfbe");
		System.out.println("reverseAns = ");
		System.out.println(reverseAns);

		//System.out.println(getKey(ans,"tyueawtmtq"));

	}

	public static char[][] getKeyArray(String key){
		char [][] matrixChiper =  new char [2][key.length()];
		int j=0;
		for (char i ='a' ; i<'a'+key.length() ; i++) {
			//System.out.println();
			matrixChiper[0][j]=i;
			matrixChiper[1][j]=key.charAt(j);
			//System.out.print("[" + matrixChiper[0][j] + "]");
			//System.out.print( "[" + matrixChiper[1][j] + "]");
			j++;
		}
		return matrixChiper;
	}

	public static String Xor(String plain_text, String init_vector) {		
		String ans = "";
		//System.out.println("palin  = " + plain_text + ", init_vector = "+ init_vector);
		for (int i=0; i<plain_text.length(); i++) {
			char charText = plain_text.charAt(i);
			//System.out.println("i = " + i+" , initvector  = " + init_vector.length());
			char charVector = init_vector.charAt(i);
			int charTextInt = charText;
			int charVectorInt = charVector;
			int numXor = charTextInt ^ charVectorInt;
			char charAns = (char) numXor;
			ans += charAns;
		}
		return ans;
	}

	public static String encrypt(String plain_text, String init_vector, String key) {
		String chiper = "";
		boolean isShorter = true;
		while(plain_text.length()>=init_vector.length()) {
			String block = plain_text.substring(0, init_vector.length());
			init_vector = blockEncrypt(block, init_vector, key);
			chiper += init_vector;
			if(plain_text.length()>=init_vector.length()) {
				plain_text = plain_text.substring(init_vector.length());
			}
			if(plain_text.length()<init_vector.length() && plain_text.length()!=0) {
				isShorter = true;
				break;
			}else {
				isShorter = false;
			}
		}
		if(isShorter)
			/* for(int i=0; i<(key.length()-plain_text.length()); i++) {
				plain_text+='\0';
				System.out.println(plain_text);
			}*/
			chiper+=blockEncrypt(plain_text, init_vector, key);
		return chiper;
	}

	public static String blockEncrypt (String plain_text, String init_vector, String key) {
		String chiper = "";
		String afterXor = Xor(plain_text,init_vector);
		//System.out.println("afterXor = " + afterXor + ", length = " + afterXor.length());
		chiper = getChiper(key, afterXor);
		return chiper;
	}

	public static String getChiper(String key , String textAfterXor) {

		char [][] keyArray = getKeyArray(key);
		String chiper = "";

		boolean flag = false;
		for (int i=0 ; i<textAfterXor.length(); i++) {
			char letter = textAfterXor.charAt(i);
			for (int j=0; j<keyArray[0].length; j++) {
				if(letter ==  keyArray[0][j]) {
					chiper += keyArray[1][j];
					flag = true;
				}
			}
			if(!flag) {
				chiper+=letter;
			}
			flag = false;
		}
		return chiper;
	}

	public static String decrypt (String encrypted, String init_vector, String key) {
		//		String plainText =blockDescrypt ( encrypted,  init_vector,  key);
		String plainText = "";
		String pl ;
		String block;
		String blockTemp = "";
		boolean isShorter = true;
		int counter=1;
		while(encrypted.length()>=init_vector.length()) {

			block = encrypted.substring(0, init_vector.length());

			if(counter==1) {
				pl = blockDescrypt(block, init_vector, key);
			}else {
				//System.out.println("block = "+ block + ", init_vector = "+ blockTemp);
				pl = blockDescrypt(block, blockTemp, key);
			}

			blockTemp=block;
			plainText += pl;
			counter++;
			if(encrypted.length()>=init_vector.length()) {
				encrypted = encrypted.substring(init_vector.length());
			}
			if(encrypted.length()<init_vector.length() && encrypted.length()!=0) {
				isShorter = true;
				break;
			}else {
				isShorter = false;
			}

		}
		if(isShorter) {
			if(counter==1) {
				plainText+=blockDescrypt(encrypted, init_vector, key);
			}else {
				plainText+=blockDescrypt(encrypted, blockTemp, key);
			}
		}
		return plainText;
	}

	public static String blockDescrypt (String chiper, String init_vector, String key) {
		String afterKey = "";
		afterKey = getChiperDecrypt(key, chiper);
		//System.out.println("afterKey = "+afterKey + ", init_vector = "+ init_vector);
		String plainText = Xor(afterKey,init_vector);
		//System.out.println("plainText = " + plainText + ", length = " + plainText.length());
		return plainText;
	}

	public static String getChiperDecrypt(String key , String textAfterXor) {

		char [][] keyArray = getKeyArray(key);
		String chiper = "";

		boolean flag = false;
		for (int i=0 ; i<textAfterXor.length(); i++) {
			char letter = textAfterXor.charAt(i);
			for (int j=0; j<keyArray[0].length; j++) {
				if(letter ==  keyArray[1][j]) {
					chiper += keyArray[0][j];
					flag = true;
					break;
				}
			}
			if(!flag) {
				chiper+=letter;
			}
			flag = false;
		}
		//System.out.println("chper: "+ chiper);
		return chiper;
	}

	public static String getKey(String chiper, String init_vector) {
		char [][] matrixChiper =  new char [2][26];
		System.out.println("First Test"); 
		char[] set1 = {'a', 'b','c','d','e','f','g','h'}; 
		int k = 8; 
		printAllKLength(set1, k ,chiper,init_vector,0);
		return key3;
	}

	public static int searchWords(String plain_text, int max) {
		int counterword=0;
		String [] words = {"was","a","in","and","were","his", "to", "an","The","At", "one", "the","through","of","thought","not","from","with","him"};
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

	public static void printAllKLength(char[] set, int k, String chiper, String init_vector,int max) { 
		int n = set.length;  
		printAllKLengthRec(set, "", n, k,chiper, init_vector, max, ""); 
	} 

	public static void printAllKLengthRec(char[] set,  String prefix, int n, int k, String chiper, String init_vector,int max,String key) { 
		// Base case: k is 0, 
		// print prefix 
		if (k == 0)  
		{   //System.out.println(prefix);
			if(duplicate(prefix)==false) {
				String plaintext=decrypt (chiper,  init_vector,prefix);
				int ans =searchWords(plaintext,max);
				if(ans>max3) {
					//System.out.println(ans+" , "+max);
					max3=ans;
					key3=prefix;
					//System.out.println(key3+", "+max3);

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
