import app.TravelApp;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main adalah titik masuk (entry point) aplikasi Sistem Pemesanan Perjalanan.
 *
 * KONSEP JAVA — Main Driver:
 *   Method main() adalah method pertama yang dipanggil JVM saat program dijalankan.
 *   Di sini kita membuat satu instance TravelApp dan menjalankan loop menu utama.
 *
 * KONSEP JAVA — while(true) loop:
 *   Loop ini terus berjalan hingga pengguna memilih "Keluar" (case 0).
 *   Ini adalah pola umum untuk menu interaktif konsol.
 *
 * KONSEP JAVA — switch expression (Java 14+):
 *   'switch (choice) { case 1 -> ...; }' lebih ringkas dari switch statement tradisional.
 *   Tidak perlu 'break' karena setiap case otomatis terisolasi.
 *
 * FILE INI SUDAH LENGKAP — tidak perlu diubah oleh tim.
 * (Loop menu, routing ke TravelApp, dan error handling input sudah ada.)
 *
 * Cara menjalankan program:
 *   1. Compile: dari folder TK-1/, jalankan:
 *        javac --enable-preview --release 17 -d bin $(find src -name "*.java")
 *   2. Run:
 *        java --enable-preview -cp bin Main
 */
public class Main {

    public static void main(String[] args) {
        TravelApp app = new TravelApp();
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   SELAMAT DATANG DI SISTEM PEMESANAN         ║");
        System.out.println("║   PERJALANAN — TravelKu                      ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // Loop utama: terus tampilkan menu sampai pengguna keluar
        while (true) {
            tampilkanMenu();

            int choice = bacaPilihanMenu(scanner);

            // Routing pilihan menu ke method di TravelApp
            switch (choice) {
                case 1 -> {
                    app.printSeparator();
                    System.out.println("  CARI & PESAN PENERBANGAN");
                    app.printSeparator();
                    app.searchAndBookFlight(scanner);
                }
                case 2 -> {
                    app.printSeparator();
                    System.out.println("  CARI & PESAN HOTEL");
                    app.printSeparator();
                    app.searchAndBookHotel(scanner);
                }
                case 3 -> {
                    app.printSeparator();
                    System.out.println("  DAFTAR SEMUA PEMESANAN");
                    app.printSeparator();
                    app.viewAllReservations();
                }
                case 4 -> {
                    app.printSeparator();
                    System.out.println("  BATALKAN RESERVASI");
                    app.printSeparator();
                    app.promptCancelReservation(scanner);
                }
                case 0 -> {
                    System.out.println("\nTerima kasih telah menggunakan TravelKu. Sampai jumpa!");
                    scanner.close();
                    return; // keluar dari main(), program selesai
                }
                default -> System.out.println("Pilihan tidak valid. Silakan masukkan angka 0-4.");
            }

            System.out.println(); // baris kosong sebelum menu berikutnya
        }
    }

    /**
     * Tampilkan menu utama ke konsol.
     */
    private static void tampilkanMenu() {
        System.out.println("════════════════════════════════════════════");
        System.out.println("              MENU UTAMA");
        System.out.println("════════════════════════════════════════════");
        System.out.println("  1. Cari & Pesan Penerbangan");
        System.out.println("  2. Cari & Pesan Hotel");
        System.out.println("  3. Lihat Semua Pemesanan");
        System.out.println("  4. Batalkan Reservasi");
        System.out.println("  0. Keluar");
        System.out.println("════════════════════════════════════════════");
        System.out.print("Pilih menu (0-4): ");
    }

    /**
     * Baca pilihan menu dari pengguna dengan penanganan error untuk input non-angka.
     *
     * KONSEP JAVA — Exception Handling:
     *   InputMismatchException dilempar oleh scanner.nextInt() jika pengguna mengetik
     *   teks (bukan angka). Blok catch menangkapnya dan mengembalikan nilai -1
     *   sehingga switch masuk ke case 'default' dan menampilkan pesan error.
     *
     * @param scanner Scanner yang digunakan untuk membaca input
     * @return angka pilihan, atau -1 jika input tidak valid
     */
    private static int bacaPilihanMenu(Scanner scanner) {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // flush sisa newline di buffer
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // buang input tidak valid dari buffer
            System.out.println("Input tidak valid. Harap masukkan angka.");
            return -1;
        }
    }
}
