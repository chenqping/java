package asymmetric.ecc.demos;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.*;
import java.security.spec.*;
import java.util.Arrays;

/*
* Openssl generate private keys in PKCS#1 by default,but Java doesn't support PKCS#1 with the following header and footer:
* -----BEGIN RSA PUBLIC KEY-----
* -----END RSA PUBLIC KEY-----
* instead, supports PKCS#8 with the following header and footer:
* -----BEGIN PRIVATE KEY-----
* -----END PRIVATE KEY-----
* so, we need to convert private key from PKCS#1 to PKCS#8 first with the following command:
*  openssl pkcs8 -topk8 -inform pem -in private.pem -outform PEM -nocrypt -out private8.pem
* reference: https://stackoverflow.com/questions/11787571/how-to-read-pem-file-to-get-private-and-public-key
* */

public class PemFileParser {

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static PrivateKey getPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        String temp = new String(keyBytes);
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
        System.out.println(privKeyPEM);

        Base64 b64 = new Base64();
        byte [] decoded = b64.decode(privKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

        KeyFactory kf = KeyFactory.getInstance("EC", "SunEC");
        return kf.generatePrivate(spec);
    }

    public static PublicKey getPublicKey(String filename) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException{

        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        String temp = new String(keyBytes);
        String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
        System.out.println(publicKeyPEM);

        Base64 b64 = new Base64();
        byte [] decoded = b64.decode(publicKeyPEM);

        //byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("EC", "SunEC");
        return kf.generatePublic(spec);
    }

    public static void main(String[] args){
        try {
            URL privKeyPem = PemFileParser.class.getClassLoader().getResource("myPrivKey.pkcs8");
            URL pubKeyPem = PemFileParser.class.getClassLoader().getResource("myPubKey.pem");
            //System.out.println(privKeyPem.getPath());

            PrivateKey privKey = getPrivateKey(privKeyPem.getPath());
            PublicKey pubKey = getPublicKey(pubKeyPem.getPath());

            Cipher encryptCipher = Cipher.getInstance("ECIES", "BC");
            encryptCipher.init(Cipher.ENCRYPT_MODE, pubKey);

            String text = "In teaching others we teach ourselves";
            System.out.println("Text: " + text);
            byte[] textBytes = text.getBytes("UTF-8");

            byte[] encrypted = encryptCipher.doFinal(textBytes);

            System.out.println("Encrypted: 0x" + (new BigInteger(1, encrypted).toString(16).toUpperCase()));
            System.out.println("Encrypted: 0x" + Arrays.toString(encrypted));

            Cipher decryptCipher = Cipher.getInstance("ECIES", "BC");
            decryptCipher.init(Cipher.DECRYPT_MODE, privKey);

            byte[] decrypted = decryptCipher.doFinal(encrypted);

            System.out.println("Decrypted: " + new String(decrypted));

        }catch (IOException ioe){
            System.out.println("Caught IOException");
        }catch (NoSuchAlgorithmException nsae){
            System.out.println("Caught NoSuchAlgorithmException");
        }catch (NoSuchProviderException nspe){
            System.out.println("Caught NoSuchProviderException");
        }catch (InvalidKeySpecException ikse){
            System.out.println("Caught InvalidKeySpecException");
        }catch (InvalidKeyException ike){
            System.out.println("Caught InvalidKeyException");
        } catch (NoSuchPaddingException nspe){
            System.out.println("Caught NoSuchPaddingException");
        }catch (BadPaddingException bpe){
            System.out.println("Caught BadPaddingException");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Caught other exception");
        }
    }
}
