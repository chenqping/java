package asymmetric.ecc.demos;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;

public class KeyGenerator {
	public static void main(String[] args) throws Exception{
		KeyPairGenerator kpg;
		kpg = KeyPairGenerator.getInstance("EC", "SunEC");
		ECGenParameterSpec ecsp;
		ecsp = new ECGenParameterSpec("secp256k1");
		//ecsp = new ECGenParameterSpec("secp192r1");
		kpg.initialize(ecsp);
		
		KeyPair kp = kpg.genKeyPair();
		PrivateKey privKey = kp.getPrivate();
		PublicKey pubKey = kp.getPublic();
		
		System.out.println(privKey.toString());
		System.out.println(pubKey.toString());
	}
}
