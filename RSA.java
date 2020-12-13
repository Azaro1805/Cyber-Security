import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class RSA {
	
	// Main for testing
	public static void main(String[] args) throws UnsupportedEncodingException {  
		
		String plaintext1 = "abcdrgdfgdfgdfd" ;
		//generate_p_q_different_primes(plaintext1);
		BigInteger public_key = BigInteger.valueOf(7);
		BigInteger private_key = BigInteger.valueOf(5008543);
		//System.out.println("plain text  = " + plaintext1);
		BigInteger n = BigInteger.valueOf(35072291);

		byte []  cipher_text_byte=encrypt(plaintext1,public_key,n);
		//for (int i = 0 ; i<cipher_text_byte.length; i++) {
		//	System.out.print(cipher_text_byte[i] + " , ");
		//}
		String a = decrypt(cipher_text_byte,private_key,n); 
		//System.out.println();
		///System.out.println("after dscrypt = " +a);
		
	}
	
	// Check if p is good with specific q
	public static int createPQ(int n , List<Integer> primeNumbers ) {
		int currentNum = primeNumbers.get(primeNumbers.size()-1);
		for (int i=0; i<primeNumbers.size()-1;i++) {
			if(primeNumbers.get(i)*currentNum>n){
				return primeNumbers.get(i);
			}
		}
		return 0;
	}

	// Generate P and Q prime numbers , calculate N and PN
	public static HashMap<BigInteger,BigInteger> primeNumbersBruteForce(int n) {
		HashMap<BigInteger,BigInteger> ans = new HashMap<BigInteger,BigInteger>();
		List<Integer> primeNumbers = new LinkedList<>();
		int counter = 0;
		int p=0;
		int q=0;
		boolean find2NUmbers = false;
		while(find2NUmbers == false) {
			Random random = new Random();
			int num = random.nextInt(n);
			if (isPrimeBruteForce(num)) {
				primeNumbers.add(num);
				if(primeNumbers.size()>1) {
					counter++;
					if(createPQ(n ,primeNumbers )>0) {
						p =createPQ(n ,primeNumbers );
						q = num;
						find2NUmbers=true;
					}
				}
			}
		}
		BigInteger p1 = BigInteger.valueOf(p);
		BigInteger q1 = BigInteger.valueOf(q);
		BigInteger p2 = BigInteger.valueOf(1);

		ans.put(p1, q1);
		System.out.println("p = " + p1);
		System.out.println("q = " + q1);
		System.out.println( "n = " + p1.multiply(q1));
		System.out.println( "PN= " + (p1.subtract(p2)).multiply((q1.subtract(p2))) );
		return ans;
	}

	// Check if number is a prime number 
	private static boolean isPrimeBruteForce(int number) {
		for (int i = 2; i < number; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	// Generate p q for plain message
	public static HashMap<BigInteger,BigInteger> generate_p_q_different_primes(String plain_message){
		BigInteger plain_text_bigint = sring_to_big_integer(plain_message);
		HashMap<BigInteger,BigInteger> ans = primeNumbersBruteForce(plain_text_bigint.intValue());		
		return ans;
	}

	// RSA encrypt plain message
	public static byte[] encrypt(String plain_message, BigInteger public_key, BigInteger n) {
		BigInteger plain_text_bigint = sring_to_big_integer(plain_message);
		BigInteger cipher_text =plain_text_bigint.modPow(public_key, n);
		byte []  cipher_text_byte= cipher_text.toByteArray();
		return cipher_text_byte;
	}

	// RSA decrypt plain message
	public static String decrypt(byte[] encrypted_message,BigInteger private_key,BigInteger n) {
		BigInteger encrypted_text_bigint = new BigInteger(encrypted_message);
		BigInteger plain_text_bigint =encrypted_text_bigint.modPow(private_key, n);
		String plain_text = new String (plain_text_bigint.toByteArray());
		return plain_text;
	}

	// Change String to Big Integer
	public static BigInteger sring_to_big_integer (String str) {
		BigInteger BigInt = new BigInteger(str.getBytes());
		if (BigInt.compareTo(BigInteger.ZERO)<0) {
			BigInt = BigInt.multiply(BigInteger.valueOf(-1));
		}
		return BigInt;	
	}
}
