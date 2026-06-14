package util;

import java.util.Random;


public final class ConfirmationNumberGenerator {

    private static final Random random = new Random();

    private ConfirmationNumberGenerator() {}


    public static int generate() {
        return 100000 + random.nextInt(900000);
    }
}
