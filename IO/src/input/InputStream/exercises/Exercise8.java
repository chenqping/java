package input.InputStream.exercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Exercise8 {
    public static void main(String[] args) throws IOException {
        System.out.println(args[0]);
        if(args == null || args.length != 1) {
            throw new IllegalArgumentException("arguments should be [input.txt] format!");
        }
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        String s;
        LinkedList<String> lines = new LinkedList<>();
        while((s = in.readLine()) != null)
            lines.add(s);
        in.close();
        Iterator<String> iterator = lines.iterator();
        while(iterator.hasNext())
            System.out.println(iterator.next());
        //while(!lines.isEmpty())
        //    System.out.println(lines.pollLast());
    }
}
