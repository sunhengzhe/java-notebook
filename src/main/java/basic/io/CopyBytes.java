package basic.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytes {
    public static void main(String[] args) throws IOException {
        try (FileInputStream in = new FileInputStream("src/main/resources/xanadu.txt");
            FileOutputStream out = new FileOutputStream("src/main/resources/outagain.txt")
        ) {
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        }
    }
}
