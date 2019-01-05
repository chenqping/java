package hash.sha256.demos;

//import com.google.common.io.BaseEncoding;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.cli.*;

public class HashSha256 {
    //public static final BaseEncoding HEX = BaseEncoding.base16().lowerCase();
    public static final int cNumber = 1;   // client number
    public static final int tNumber = 100000;  // task nmuber
    public static void main(String[] args) throws Exception{
        byte[] input = "012345abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
        int client = cNumber,temp = tNumber;
        Options options = new Options();
        Option c = new Option("c", "client", true,"client number");
        c.setRequired(false);
        options.addOption(c);
        Option t = new Option("t", "task",true,"task number");
        t.setRequired(false);
        options.addOption(t);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        try{
            CommandLine cmd = parser.parse(options,args);
            client = (cmd.getOptionValue("c") != null && cmd.getOptionValue("c").length() != 0) ? Integer.parseInt(cmd.getOptionValue("c")):client;
            temp = (cmd.getOptionValue("t") != null && cmd.getOptionValue("t").length() != 0) ? Integer.parseInt(cmd.getOptionValue("t")):temp;
        }catch (ParseException pe){
            System.out.println(pe.getMessage());
            formatter.printHelp("HashSha256:", options);
            System.exit(1);
        }catch (NumberFormatException nfe){
            System.out.println(nfe.getMessage());
            formatter.printHelp("HashSha256:", options);
            System.exit(1);
        }
        final int task = temp;
        System.out.println("client: " + client + " task: " + task);
        Thread[] threads = new Thread[client];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < client; i ++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        for (int j = 0 ; j < task; j ++){
                            md.update(input);
                            md.digest();
                            //System.out.println(Thread.currentThread().getName() + "-Task-" + j + ": " + HEX.encode(md.digest()));
                            md.reset();
                        }
                    }catch (NoSuchAlgorithmException nsae){
                        nsae.printStackTrace();
                    }
                }
            }, "Client-" + i);
            threads[i].start();
        }
        for (int i = 0; i < cNumber; i ++){
            threads[i].join();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Running " + client*task + " times SHA-256 hash cost " + (endTime-startTime) + "ms, average: " + task*client*1000/(endTime-startTime) + " times per second");
    }
}
