package pattern;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherCase {
    public final static String sql = "insert into /*       encode : hex    */ t_transaction(Fversion,Fmeta,Finputs,Foutputs,Flocktime) /*       encode : hex    */  values(1,'AQ4','[{\"index\":0,\"output_point\":{\"hash\":\"0000000000000000000000000000000000000000000000000000000000000000\",\"index\":-1,\"issueNo\":\"1003\"},\"voucher\":\"5wCB2mfeicsG9c8dt9ECtsqYG9UT3oFxW7opPyBbhWwL8rRaBAz4JTxuL7poj8awQ2ipcKLHHmKWnmHpdibcKVwzjqycrjxWaGiNu8jd4sKqRvD9C9V8pZFB9RiQA1LxfLMehFZSEQ4ZYwUC4r5NZv1\"}]','[{\"condition\":\"6GS968dzAR1radckRVDh5J2HVUPtsWcnwt6d4Lb\",\"amount\":100,\"address\":\"1KzBWDAcf66fyZ1qGb2r6YZp3CfuRgfdDr\",\"data\":\"AQ4\",\"index\":0,\"id\":\"132RuqxHaLouDxtX5GgWhj2bjaHxWMGcM4rDmDG6vUR6WryqFVCNevUMqgoX8d\"}]',0);";
    public static void main(String[] args){
        Pattern p = Pattern.compile("/\\*\\s*encode\\s*:\\s*hex\\s*\\*/");
        Matcher m = p.matcher(sql);
        //System.out.println(m.matches());
        if (m.find()){
            System.out.println("found!");
        }
        while (m.find()){
            System.out.println(m.group()+" ");
        }
    }
}
