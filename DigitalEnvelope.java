import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class DigitalEnvelope {
	
	// Main fot testing 
		public static void main(String[] args) throws UnsupportedEncodingException {
			
			BigInteger public_key = BigInteger.valueOf(7);
			BigInteger n = BigInteger.valueOf(35072291);
			BigInteger private_key = BigInteger.valueOf(5008543);
			
			HashMap<byte[],String> env =create_envelope("tyueawtmtq", "dog go home", "dhagcfbe", public_key, n );
			String openEV = open_envelope(env, "tyueawtmtq", private_key, n);
			//System.out.println(openEV);
		}
	
	// Create envelope with the encrypt text and key
	public static HashMap<byte[],String> create_envelope(String init_vector, String plain_text, String symmetric_key,
			BigInteger public_key, BigInteger n){
		HashMap<byte[],String> envlope =  new HashMap<byte[],String>();
		String encrypted = CipherBlockChaining.encrypt(plain_text, init_vector, symmetric_key);
		BigInteger symmetric_key_bigInt = new BigInteger(symmetric_key.getBytes());
		byte[] key= RSA.encrypt(symmetric_key, public_key, n);
		envlope.put(key, encrypted);
		return envlope;
	}

	// Open the envelope and generate plain text
	public static String open_envelope(HashMap<byte[],String> envelope, String init_vector, BigInteger private_key,
			BigInteger n) {
		String semtricKey="";
		String plain_Text = "";
		for(Map.Entry<byte[],String> entry : envelope.entrySet()) {
			semtricKey=RSA.decrypt(entry.getKey(), private_key, n);
			plain_Text=CipherBlockChaining.decrypt(entry.getValue(), init_vector, semtricKey);
			return plain_Text;
		}
		return null;
	}
	
	
}
