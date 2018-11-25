package marques.ifib;


import java.util.Random;

public class OAuthParams {
    public static final String clientID = "2q9RaZmtEdDx7ITM72dGNc8jR4fqBKD3oUoXEsLW";
    public static final String clientSecret = "fic2k2KmZhKOVTZ5yxiHw8JbDEldnZRMrnJW4cGLVV2zKxv6RVyZSJWz0yzi0rkVUC8s1zw6s7mMzXmAfO2x6QYVX2jmqemVU9JbLtAbBdhVdjgj6d5tboZRTSAivsMj";
    public static final String redirectUri = "apifib://raco";
    public static String responseType = "code";
    public static String state;

    public static String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        state = saltStr;
        return saltStr;
    }
}