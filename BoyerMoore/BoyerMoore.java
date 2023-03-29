import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BoyerMoore {

    public static int boyerMoore(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();

        if (m == 0) {
            return 0;
        }

        int[] last = new int[10000];
        for (int i = 0; i < 256; i++) {
            last[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            last[pattern.charAt(i)] = i;
        }

        int i = m - 1;
        int j = m - 1;
        int count = 0;

        long startTime = System.nanoTime();

        while (i < n) {
            count++;
            if (text.charAt(i) == pattern.charAt(j)) {
                if (j == 0) {
                    long endTime = System.nanoTime();
                    System.out.println("Execution time: " + (endTime - startTime) + "ns");
                    System.out.println("Number of iterations: " + count);
                    return i;
                }
                i--;
                j--;
            } else {
                i += m - Math.min(j, 1 + last[text.charAt(i)]);
                j = m - 1;
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) + "ns");
        System.out.println("Number of iterations: " + count);
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("random_strings.txt");
        Scanner scanner = new Scanner(file);
        int nabor = 1;
        String pattern = "abc";
        while (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            System.out.println(nabor + " набор (длина " + text.length() + " букв)");
            nabor++;
            System.out.println(boyerMoore(text, pattern));
        }
    }
}