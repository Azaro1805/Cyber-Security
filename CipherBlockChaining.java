public class CipherBlockChaining {

	// Main for testing 
	public static void main(String[] args) {
		String plain_text = "masses on the spring, and they both take the same time to complete one oscillation, we can define them as having the same mass.\r\n"
				+ "Since I started this chapter by highlighting the relationship between conservation laws and symmetries, you're probably wondering what symmetry is related to conservation of mass. I'll come back to that at the end of the chapter.\r\n"
				+ "When you learn about a new physical quantity, such as mass, you need to know what units are used to measure it. This will lead us to a brief digression on the metric system, after which we'll come back to physics.\r\n"
				+ "" ;
		String init_vector = "tyueawtmtq";
		String key = "dhagcfbe";
		String encrpty = encrypt(plain_text , init_vector , key);
		String ans = decrypt(encrpty, init_vector , key);
		//"tyueawtmtq" , "dhagcfbe"
		System.out.println(ans);
	}

	// Create key array for change vaules in encryption and descryption
	public static char[][] getKeyArray(String key){
		char [][] matrixChiper =  new char [2][key.length()];
		int j=0;
		for (char i ='a' ; i<'a'+key.length() ; i++) {
			matrixChiper[0][j]=i;
			matrixChiper[1][j]=key.charAt(j);
			j++;
			if(i == 'z') {
				break;
			}
		}
		if(key.length()>j) {
			for (char h ='A' ; h<'A'+key.length()-26 ; h++) {
				matrixChiper[0][j]=h;
				matrixChiper[1][j]=key.charAt(j);
				j++;
			}
		}
		return matrixChiper;
		
	}

	// Xor between plain text and init vector
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

	// Encrypt plain text with key and init vector
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
			chiper+=blockEncrypt(plain_text, init_vector, key);
		return chiper;
	}

	// Encrypt block with key and init vector
	public static String blockEncrypt (String plain_text, String init_vector, String key) {
		String chiper = "";
		String afterXor = Xor(plain_text,init_vector);
		chiper = getChiper(key, afterXor);
		return chiper;
	}

	// Changes values using the array we created with the key and characters to be changed (encrypt)
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

	// Decrypt encrypted text with key and init vector
	public static String decrypt (String encrypted, String init_vector, String key) {
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

	// Decrypt block with key and init vector
	public static String blockDescrypt (String chiper, String init_vector, String key) {
		String afterKey = "";
		afterKey = getChiperDecrypt(key, chiper);
		String plainText = Xor(afterKey,init_vector);
		return plainText;
	}

	// Changes values using the array we created with the key and characters to be changed (descrypt)
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

}