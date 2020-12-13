import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class DiffieHellman {

	// Main for test
	public static void main(String[] args) throws UnsupportedEncodingException{
		BigInteger q = BigInteger.valueOf(353);
		BigInteger private_Xa = BigInteger.valueOf(97);
		BigInteger private_Xb = BigInteger.valueOf(233);
		BigInteger[] ans =diffie_hellman(q ,private_Xa , private_Xb);
		//for (int i=0 ; i<ans.length; i++) {
		//	System.out.print ( ans[i] +" , " );
		//}
		//System.out.println();
		String sender_info = "Group 5";
		BigInteger sender_public_key = ans[2];
		String CA_info = "CyberSecurity2020";
		//System.out.println("after digest");
		byte[] digest = get_digest(sender_info,  sender_public_key,  CA_info);
		//for (int i=0 ; i<digest.length; i++) {
		//	System.out.print ( digest[i] +" , " );
		//}
		//*/
	}

	// Generate all the Keys
	public static BigInteger[] diffie_hellman(BigInteger q, BigInteger private_Xa, BigInteger private_Xb) {
		BigInteger[] allKeys = new BigInteger[7];
		allKeys[1] = private_Xa; // Xa
		allKeys[3] = private_Xb;  // Xb
		allKeys[0] =  find_prime_num(q); // Alpha 
		allKeys[2] = allKeys[0].modPow(private_Xa, q); //Ya
		allKeys[4] = allKeys[0].modPow(private_Xb, q); //Yb
		allKeys[5] = allKeys[4].modPow(private_Xa, q); //Ka
		allKeys[6] = allKeys[2].modPow(private_Xb, q); //Kb
		for(int i=0 ; i<allKeys.length; i++) {
			if(allKeys[i].compareTo(BigInteger.ZERO)<0) {
				allKeys[i] =allKeys[i].multiply(BigInteger.valueOf(-1));
			}
		}
		return allKeys;
	}

	// Find prime num in order to create alpha
	public static BigInteger find_prime_num(BigInteger q) {
		boolean ans;
		BigInteger num = BigInteger.valueOf(1);
		for (int i=2 ; i<q.intValue()-1 ; i++) {
			ans = is_prime_root(q , BigInteger.valueOf(i));
			if(ans)
				return BigInteger.valueOf(i);
		}
		return BigInteger.valueOf(-1);
	}

	// Check if number is prime root of q 
	public static boolean is_prime_root(BigInteger q , BigInteger a) { 
		Map<BigInteger, Boolean> primeFactor = new HashMap<BigInteger, Boolean> ();
		BigInteger num = BigInteger.valueOf(1);
		BigInteger num2 = BigInteger.valueOf(1);
		for (int i=0 ; i<=q.intValue() ; i++) {
			num2 = a.pow(i);
			primeFactor.put(num2.mod(q), true);
		}
		if(primeFactor.size()==q.intValue()-1)
			return true;
		return false;
	}

	// Genertae big array from 3 arrays
	public static byte[] make_byte_array(String sender_info, BigInteger sender_public_key, String CA_info) throws UnsupportedEncodingException {
		byte[] toChapter1 = sender_info.getBytes("us-ascii");
		byte[] toChapter2 = CA_info.getBytes();
		byte[] toChapter3 = sender_public_key.toByteArray();
		int length = toChapter1.length+toChapter2.length+toChapter3.length;
		byte[] total = new byte [length];

		for (int i=0 ; i<toChapter1.length ; i++) {
			total[i]=toChapter1[i];
		}
		int j=0;
		for (int i=toChapter1.length ; i<toChapter2.length+toChapter1.length ; i++) {
			total[i]=toChapter2[j];
			j++;
		}
		j=0;
		for (int i=(toChapter1.length)+(toChapter2.length) ; i<total.length ; i++) {
			total[i]=toChapter3[j];
			j++;
		}
		/*
		System.out.println("total Stirng bytes : ");
		for (int i=0 ; i<total.length; i++) {
			System.out.print ( total[i] +" , " );
		}
		*/
		return  total;
	}

	// Create digest  from : sender info , sender public key , CA info
	public static byte[] get_digest(String sender_info, BigInteger sender_public_key, String CA_info) throws UnsupportedEncodingException  {

		byte [] byte_array =make_byte_array( sender_info,  sender_public_key,  CA_info);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(byte_array);
			byte[] toChapter1Digest = md.digest();
			for( int i=0; i<toChapter1Digest.length ; i++ ) {
				if(toChapter1Digest[i]<0) {
					toChapter1Digest[i]=(byte) (toChapter1Digest[i]*(-1));
				}
			}
			return toChapter1Digest;
		} catch (Exception e) {

		}
		return null;		
	}

	// Encrypt digest with RSA 
	public static byte[] sign_certificate(byte[] hash, BigInteger public_key, BigInteger n) {
		String digist_to_encrypt = new String(hash);
		return RSA.encrypt(digist_to_encrypt, public_key, n);
	}

	// Descrypt digest with RSA 
	public static byte[] get_hash_from_certificate(byte[] signed_certificate,BigInteger private_key,BigInteger n) {
		String digist_descrypt = RSA.decrypt(signed_certificate, private_key, n);
		BigInteger digist_descrypt_BigInteger = RSA.sring_to_big_integer(digist_descrypt);
		byte[] digist_descrypt_byte= digist_descrypt.getBytes();
		return digist_descrypt_byte;
	}


}
