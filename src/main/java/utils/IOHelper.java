package utils;

import java.util.Scanner;

/**
 * @author Bayurov
 * Плюшки для работы с вводом выводом
 */
public class IOHelper {

    private static Scanner s = new Scanner(System.in);

    public static void print(String message) {
        System.out.println(message);
    }

    public static String inNextLine() {
        return s.nextLine();
    }
}
