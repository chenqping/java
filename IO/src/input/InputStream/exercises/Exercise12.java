package input.InputStream.exercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

public class Exercise12 {
    public static void main(String[] args) throws IOException {
        System.out.println(args[0]);
        if(args == null || args.length != 2) {
            throw new IllegalArgumentException("arguments should be [input.txt] [output.txt] format!");
        }
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        String s;
        LinkedList<String> lines = new LinkedList<>();
        while((s = in.readLine()) != null)
            lines.add(s);
        in.close();
        PrintWriter out = new PrintWriter(args[1]);
        int lineCount = 1;
        Iterator<String> iterator = lines.iterator();
        while(iterator.hasNext())
            //System.out.println(iterator.next());
            out.println(lineCount++ + ": " + iterator.next());
        out.close();
        //while(!lines.isEmpty())
        //    System.out.println(lines.pollLast());
    }
}
