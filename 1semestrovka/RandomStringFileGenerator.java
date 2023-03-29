import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RandomStringFileGenerator {

    public static void main(String[] args) {
        File file = new File("random_strings.txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 5000; i++) {
                String randomString = RandomStringGenerator.generateRandomString("abcdefghijklmopqrstuvwxyz",
                        i+1);
                writer.write(randomString + "\n");
            }
            writer.close();
            System.out.println("File created: " + file.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}