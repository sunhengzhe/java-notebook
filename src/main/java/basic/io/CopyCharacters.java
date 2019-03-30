package basic.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCharacters {
    public static void main(String[] args) throws IOException {
        try (FileReader inputStream = new FileReader("src/main/resources/xanadu.txt");
             FileWriter outputStream = new FileWriter("src/main/resources/outagain.txt")
        ) {
            int c;

            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
        }
    }
}
