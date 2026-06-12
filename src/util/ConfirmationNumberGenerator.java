package util;

import java.util.Random;

/**
 * Utility class untuk menghasilkan nomor konfirmasi acak 6 digit.
 *
 * KONSEP JAVA — final class:
 *   Keyword 'final' pada class mencegah class lain meng-extend (mewarisi) class ini.
 *   Ini tepat untuk utility class yang hanya berisi method statis dan tidak dirancang
 *   untuk di-subclass. Contoh lain dari final class di Java: String, Integer.
 *
 * KONSEP JAVA — static method & private constructor:
 *   Semua method adalah 'static' sehingga bisa dipanggil langsung tanpa instansiasi:
 *     int nomorKonfirmasi = ConfirmationNumberGenerator.generate();
 *   Constructor di-private-kan untuk mencegah pembuatan objek yang tidak perlu.
 *
 * FILE INI SUDAH LENGKAP — tidak perlu diubah.
 */
public final class ConfirmationNumberGenerator {

    private static final Random random = new Random();

    // Private constructor: class ini tidak boleh diinstansiasi
    private ConfirmationNumberGenerator() {}

    /**
     * Generate nomor konfirmasi acak 6 digit antara 100000 dan 999999.
     *
     * Cara kerja:
     *   random.nextInt(900000) → angka 0–899999
     *   + 100000               → angka 100000–999999 (selalu 6 digit)
     *
     * @return nomor konfirmasi 6 digit unik
     */
    public static int generate() {
        return 100000 + random.nextInt(900000);
    }
}
