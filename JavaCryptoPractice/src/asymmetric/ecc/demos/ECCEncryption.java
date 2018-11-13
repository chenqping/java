package asymmetric.ecc.demos;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;


/* secp256k1 */
public class ECCEncryption {
	
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	public static void main(String[] args) throws Exception {
		KeyPairGenerator kpg;
		kpg = KeyPairGenerator.getInstance("EC", "SunEC");
		
		ECGenParameterSpec ecsp;
		ecsp = new ECGenParameterSpec("secp256k1");
		kpg.initialize(ecsp);
		
		KeyPair kp = kpg.genKeyPair();
		PrivateKey privKey = kp.getPrivate();
		PublicKey pubKey = kp.getPublic();
		System.out.println(privKey.toString());
		System.out.println(pubKey.toString());
		
		Cipher encryptCipher = Cipher.getInstance("ECIES", "BC");
		encryptCipher.init(Cipher.ENCRYPT_MODE, pubKey);
		
		String text = "In teaching others we teach ourselves";
		System.out.println("Text: " + text);
		byte[] baText = text.getBytes("UTF-8");
		
		byte[] encrypted = encryptCipher.doFinal(baText);
		
		System.out.println("Encryted: 0x" + (new BigInteger(1, encrypted).toString(16).toUpperCase()));
		System.out.println("Encryted: 0x" + Arrays.toString(encrypted));
		
		Cipher decryptCipher = Cipher.getInstance("ECIES", "BC");
		decryptCipher.init(Cipher.DECRYPT_MODE, privKey);
		
		byte[] decrypted = decryptCipher.doFinal(encrypted);
		
		System.out.println("Decryted: " + new String(decrypted));
		
	}
}
