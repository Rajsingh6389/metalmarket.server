package MaterialMart.Config;

import java.util.Base64;

public class Base64KeyGenerator {
    public static void main(String[] args) {
        String secret = "mySuperSecretKeyThatIsLongEnough123!";
        String encoded = Base64.getEncoder().encodeToString(secret.getBytes());
        System.out.println(encoded);
    }
}
