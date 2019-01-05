package input.InputStream.exercises;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

public class Exercise7 {
    public static void main(String[] args) throws IOException{
        URL input = Exercise7.class.getClassLoader().getResource("input.txt");

        BufferedReader in = new BufferedReader(new FileReader(input.getFile()));
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
