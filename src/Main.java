import app.TravelApp;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        TravelApp app = new TravelApp();
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   SELAMAT DATANG DI SISTEM PEMESANAN         ║");
        System.out.println("║   PERJALANAN — TravelKu                      ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        while (true) {
            tampilkanMenu();
            int choice = bacaPilihanMenu(scanner);

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
                    return;
                }
                default -> System.out.println("Pilihan tidak valid. Silakan masukkan angka 0-4.");
            }

            System.out.println();
        }
    }

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

    private static int bacaPilihanMenu(Scanner scanner) {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Input tidak valid. Harap masukkan angka.");
            return -1;
        }
    }
}
