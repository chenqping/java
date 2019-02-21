package examples;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class HelloWorld {
    public interface CLibrary extends Library{
        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary(("cdll"), CLibrary.class);
/*        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
                        CLibrary.class);
        void printf(String format, Object... args);*/
        int add(int a, int b);
    }
    public static void main(String[] args){
/*        CLibrary.INSTANCE.printf("Hello, World\n");
        System.out.println(System.getProperty("java.library.path"));
        for (int i = 0; i < args.length; i++){
            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
        }*/
        int sum = CLibrary.INSTANCE.add(2,2);
        System.out.println(sum);
    }
}
