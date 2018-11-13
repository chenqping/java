package asymmetric.ecc.utility;
import java.security.*;
import java.security.spec.*;

public class EncryptHelper {
	public static KeyPair keyGenerator() {
		return null;
	}
	
	public static boolean keyAgreement(byte[] secret) {
		return true;
	}
	
	public static byte[] eccsdaSign(PrivateKey privKey, byte[] text) {
		return null;
	}
	
	public static boolean eccsdaVerify(PublicKey pubKey, byte[] text, byte[] signature) {
		return true;
	}
	
	public static byte[] eccEncrypt(PublicKey pubKey, byte[] text) {
		return null;
	}
	
	public static byte[] eccDecrypt(PrivateKey privKey, byte[] encryted) {
		return null;
	}
}

