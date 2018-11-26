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
        String privKeyPEM = temp.replace("-----BEGIN ECC PRIVATE KEY-----", "");
        privKeyPEM = privKeyPEM.replace("-----END ECC PRIVATE KEY-----", "");
        //System.out.println(privKeyPEM);

        Base64 b64 = new Base64();
        byte [] decoded = b64.decode(privKeyPEM);

        //byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        //ECPrivateKeySpec spec = new ECPrivateKeySpec(keyBytes);
        //X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

        KeyFactory kf = KeyFactory.getInstance("EC", "SunEC");
        //ECGenParameterSpec ecsp;
        //ecsp = new ECGenParameterSpec("secp256k1");

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
        String publicKeyPEM = temp.replace("-----BEGIN ECC PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.replace("-----END ECC PUBLIC KEY-----", "");
        //System.out.println(publicKeyPEM);

        Base64 b64 = new Base64();
        byte [] decoded = b64.decode(publicKeyPEM);

        //byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("EC", "SunEC");
        return kf.generatePublic(spec);
    }

    public static void main(String[] args){
        try {
            URL privKeyPem = PemFileParser.class.getClassLoader().getResource("private.key");
            URL pubKeyPem = PemFileParser.class.getClassLoader().getResource("public.pem");
            //System.out.println(privKeyPem.getPath());

            PrivateKey privKey = getPrivateKey(privKeyPem.getPath());
            PublicKey pubKey = getPublicKey(pubKeyPem.getPath());

            Cipher encryptCipher = Cipher.getInstance("ECIES", "BC");
            encryptCipher.init(Cipher.ENCRYPT_MODE, pubKey);

            String text = "In teaching others we teach ourselves";
            System.out.println("Text: " + text);
            byte[] textBytes = text.getBytes("UTF-8");

            byte[] encrypted = encryptCipher.doFinal(textBytes);

            System.out.println("Encryted: 0x" + (new BigInteger(1, encrypted).toString(16).toUpperCase()));
            System.out.println("Encryted: 0x" + Arrays.toString(encrypted));

            Cipher decryptCipher = Cipher.getInstance("ECIES", "BC");
            decryptCipher.init(Cipher.DECRYPT_MODE, privKey);

            byte[] decrypted = decryptCipher.doFinal(encrypted);

            System.out.println("Decryted: " + new String(decrypted));

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
            System.out.println("Caught other exception");
        }
    }
}
