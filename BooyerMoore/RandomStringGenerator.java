import java.util.Random;

public class RandomStringGenerator {

    public static String generateRandomString(String allowedChars, int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(allowedChars.length());
            sb.append(allowedChars.charAt(index));
        }
        return sb.toString();
    }
}