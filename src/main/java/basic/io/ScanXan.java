package basic.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ScanXan {
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("src/main/resources/xanadu.txt")))) {

            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        }
    }
}
