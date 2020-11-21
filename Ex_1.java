
public class Ex_1 {

	public static void main(String[] args) {

		/*
		char ch = 'A';

        int ascii = ch;
        // You can also cast char to int
        int castAscii = (int) ch;

        System.out.println("The ASCII value of " + ch + " is: " + ascii);
        System.out.println("The ASCII value of " + ch + " is: " + castAscii);
		 */
		String s = "ABCDQRSTAB";
		String v ="0000000000";
		//System.out.println(s.substring(0, v.length()));
				
		String ans = encrypt("ABCDQRSTAB" , "0000000000", "bacdeghf");
		System.out.println("ans = "+ ans);
		
		String ans2 = encrypt("AAB" , "00", "abcdefghijklmnopa");
		System.out.println("ans2 = "+ans2);

	}

	/*
	public static void ( OBJECT keyArray [][]  ) {
		for (int i=0 ; i<keyArray.length; i++) {
			for (int j=0 ; j<keyArray[0].length; j++) {
				System.out.print("["+ keyArray[i][j]+"]");
			}
			System.out.println();
		}
		System.out.println();
	}
	 */

	public static String Xor(String plain_text, String init_vector) {		

		String ans = "";

		for (int i=0; i<plain_text.length(); i++) {
			char charText = plain_text.charAt(i);
			char charVector = init_vector.charAt(i);
			int charTextInt = charText;
			int charVectorInt = charVector;
			int numXor = charTextInt ^ charVectorInt;
			char charAns = (char) numXor;
			ans += charAns;
		}
		return ans;
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

	public static String blockEncrypt (String plain_text, String init_vector, String key) {
		String chiper = "";
		String afterXor = Xor(plain_text,init_vector);
		//System.out.println("afterXor = " + afterXor + ", length = " + afterXor.length());
		chiper = getChiper(key, afterXor);
		return chiper;
	}

	public static String encrypt(String plain_text, String init_vector, String key) {
		String chiper = "";
		boolean isShorter = false;
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
			}
		}
		if(isShorter)
			chiper+=blockEncrypt(plain_text, init_vector, key);
		return chiper;
	}


}
