package hash.sm3.demos;

//import com.google.common.io.BaseEncoding;
import com.tenpay.smAlgo.SM3Algo;
import java.io.UnsupportedEncodingException;

public class HashSM3 {
    //public static final BaseEncoding HEX = BaseEncoding.base16().lowerCase();
    public static final int cNumber = 1;   // client number
    public static final int tNumber = 100000;  // task nmuber

    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        int client = cNumber, temp = tNumber;
        byte[] input = "012345abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
        for(int i=0; i < args.length; i++) {
            if(args[i].equals("-client")) {
                client = Integer.parseInt(args[++i]);
                continue;
            }
            if(args[i].equals("-task")) {
                temp = Integer.parseInt(args[++i]);
                continue;
            }
            System.out.println("HashSM3  [-client_count <client_count>] [-pre_client_task <pre_client_task>]");
            System.out.println("Example:");
            System.out.println("HashSM3 -client 10 -task 100");
            return;
        }
        final int task = temp;
        System.out.println("client: " + client + " task: " + task);
        Thread[] threads =new Thread[client];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < client; i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < task; j++){
                        SM3Algo sm3Algo = new SM3Algo();
                        sm3Algo.update(input);
                        sm3Algo.digest();
                        //System.out.println(Thread.currentThread().getName() + "-Task-" + j + ": " + HEX.encode(sm3Algo.digest()));
                    }
                }
            }, "Client-" + i);
            threads[i].start();
        }
        for (int i =0 ;i < client ; i++){
            threads[i].join();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Running " + client*task + " times SM3 hash cost " + (endTime-startTime) + "ms, average: " + task*client*1000/(endTime-startTime) + " times per second");
    }
}
