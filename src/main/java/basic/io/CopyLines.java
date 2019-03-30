package basic.io;

import java.io.*;

public class CopyLines {
    public static void main(String[] args) throws IOException {
        try (BufferedReader inputStream = new BufferedReader(new FileReader("src/main/resources/xanadu.txt"));
             PrintWriter outputStream = new PrintWriter(new FileWriter("src/main/resources/outagain.txt"))
        ) {
            String l;

            while ((l = inputStream.readLine()) != null) {
                outputStream.println(l);
            }
        }
    }
}
