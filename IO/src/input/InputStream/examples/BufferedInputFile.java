package input.InputStream.examples;
import java.io.*;
import java.net.URL;

public class BufferedInputFile {
    public static String read(String filename)throws IOException{
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while((s = in.readLine()) != null)
            sb.append(s + "\n");
        in.close();
        return sb.toString();
    }
    public static void main(String[] args) throws IOException{
        URL input = BufferedInputFile.class.getClassLoader().getResource("input.txt");
        System.out.print(read(input.getFile()));
    }
}
