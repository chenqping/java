package asymmetric.sm2.demos;
import com.tenpay.smAlgo.SM2Algo;

import java.util.Arrays;

public class SM2Signature {
    private static byte[] input = "abcdefghijklmnopqrstuvwxyz".getBytes();
    private static byte[] id = "miracle".getBytes();
    private static SM2Algo sm2Algo;
    //private static final ThreadLocal<SM2Signature> threadLocal = new ThreadLocal<SM2Signature>();
    private static SM2Algo newSM2Algo(){
        if(sm2Algo == null){
            sm2Algo = new SM2Algo();
            sm2Algo.initCtx();
        }
        return sm2Algo;
    }
    private static void destroySM2algo(){
        if ( sm2Algo != null){
            sm2Algo.freeCtx();
        }
    }

    public static byte[][] generateKeyPair() throws Exception{
        SM2Algo sm2Algo = newSM2Algo();
        byte[] privKey = sm2Algo.generatePrivateKey();
        byte[] pubKey = sm2Algo.generatePublicKey(privKey);
        return new byte[][]{privKey,pubKey};
    }

    public byte[] sign(byte[] data, byte[] id, byte[] pubKey, byte[] privKey){
        return doSign(data, id, pubKey, privKey);
    }

    public static byte[] doSign(byte[] data, byte[] id, byte[] pubKey, byte[] privKey){
        SM2Algo sm2Algo = newSM2Algo();
        try {
            return sm2Algo.sign(data,id,pubKey,privKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean verify(byte[] data, byte[] id, byte[] signature, byte[] pubKey){
        return doVerify(data, id, signature, pubKey);
    }

    public static boolean doVerify(byte[] data, byte[] id , byte[] signature, byte[] pubKey){
        SM2Algo sm2Algo = newSM2Algo();
        try{
            return sm2Algo.verify(data,id, signature,pubKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args){
        SM2Algo sm2Algo = newSM2Algo();
        try{
            byte[] priv = sm2Algo.generatePrivateKey();
            byte[] pub = sm2Algo.generatePublicKey(priv);

            byte[] signature = doSign(input,id,pub,priv);
            if (doVerify(input,id,signature,pub)){
                System.out.println("Verify SM2 Sign Ok");
            }
            byte[] priv1 = sm2Algo.generatePrivateKey();
            byte[] pub1 = sm2Algo.generatePublicKey(priv1);
            signature = doSign(input,id,pub1,priv1);
            if (doVerify(input,id,signature,pub1)){
                System.out.println("Verify SM2 Sign Ok");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            destroySM2algo();
        }
    }
}
