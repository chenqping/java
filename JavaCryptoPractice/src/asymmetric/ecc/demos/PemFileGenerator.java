package asymmetric.ecc.demos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
 
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
 
class PemFile {
	
	private PemObject pemObject;
	
	public PemFile (Key key, String description) {
		this.pemObject = new PemObject(description, key.getEncoded());
	}
	
	public void write(String filename) throws IOException {
		PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)));
		try {
			pemWriter.writeObject(this.pemObject);
		} finally {
			pemWriter.close();
		}
	}
}

public class PemFileGenerator{
	protected final static Logger LOGGER = Logger.getLogger(PemFileGenerator.class);
	
	public static final int KEY_SIZE = 1024;
 
	public static void main(String[] args) throws InvalidAlgorithmParameterException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		LOGGER.info("BouncyCastle provider added.");
		
		KeyPair keyPair = generateECCKeyPair();
		ECPrivateKey priv = (ECPrivateKey) keyPair.getPrivate();
		ECPublicKey pub = (ECPublicKey) keyPair.getPublic();
		
		writePemFile(priv, "PRIVATE KEY", "private.key");
		writePemFile(pub, "PUBLIC KEY", "public.pem");
		
	}
 
	private static KeyPair generateECCKeyPair() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		
		KeyPairGenerator kpg;
		kpg = KeyPairGenerator.getInstance("EC", "SunEC");
		ECGenParameterSpec ecsp;
		ecsp = new ECGenParameterSpec("secp256k1");
		//ecsp = new ECGenParameterSpec("secp192r1");
		kpg.initialize(ecsp);
		
		KeyPair kp = kpg.genKeyPair();
		return kp;
	}
 
	private static void writePemFile(Key key, String description, String filename)
			throws IOException {
		PemFile pemFile = new PemFile(key, description);
		pemFile.write(filename);
		
		LOGGER.info(String.format("%s successfully writen in file %s.", description, filename));
	}
}
