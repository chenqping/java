package input.InputStream.exercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Exercise9 {
    public static void main(String[] args) throws IOException {
        if(args == null || args.length != 2) {
            throw new IllegalArgumentException("arguments should be [input.txt] format!");
        }
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        String s;
        LinkedList<String> lines = new LinkedList<>();
        while((s = in.readLine()) != null)
            lines.add(s);
        in.close();
        Iterator<String> iterator = lines.iterator();
        String line;
        while(iterator.hasNext()){
            line = iterator.next();
            if (line.contains(args[1]))
                System.out.println(line);
        }
    }
}
