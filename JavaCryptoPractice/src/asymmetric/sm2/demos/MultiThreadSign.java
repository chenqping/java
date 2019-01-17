package asymmetric.sm2.demos;

import com.tenpay.smAlgo.SM2Algo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e){
        System.out.println("Caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory{
    public Thread newThread(Runnable r){
        //System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
        //System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        //System.out.println("eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}

public class MultiThreadSign implements Runnable {
    public static final byte[] data = "abcdefghijklmnopqrstuvwxyz".getBytes();
    public static final byte[] id = "qq.com".getBytes();
    public static byte[] priv, pub;
    public static SM2Algo sm2Algo;
    static {
        sm2Algo = new SM2Algo();
        sm2Algo.initCtx();
        try {
           priv = sm2Algo.generatePrivateKey();
           pub = sm2Algo.generatePublicKey(priv);
           System.out.println(new String(priv));
           System.out.println(new String(pub));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try{
            byte[] signature = sm2Algo.sign(data, id, pub, priv);
            if (sm2Algo.verify(data, id, signature, pub))
                System.out.println(Thread.currentThread().getName() + ": Verify OK");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws InterruptedException{
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        Thread[] threads = new Thread[100];
        for (int i = 0; i< 100; i++){
            threads[i] = new Thread(new MultiThreadSign(), "Thread-"+ i);
            //exec.execute(new MultiThreadSign());
            threads[i].start();
        }
        for (int i = 0; i< 100; i++){
            threads[i].join();
        }
        //exec.shutdown();
    }
}
