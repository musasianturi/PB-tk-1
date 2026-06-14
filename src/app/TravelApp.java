package app;

import exceptions.ReservationNotFoundException;
import model.Flight;
import model.FlightReservation;
import model.Hotel;
import model.HotelReservation;
import model.Reservation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Logika bisnis utama aplikasi TravelKu.
 * Mengelola tiga koleksi data (flights, hotels, reservations) dan menyediakan
 * method untuk setiap fitur menu yang dipanggil dari {@link Main}.
 */
public class TravelApp {

    private final ArrayList<Flight> flights = new ArrayList<>();
    private final ArrayList<Hotel> hotels = new ArrayList<>();
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public TravelApp() {
        initSampleData();
    }

    // =================== DATA AWAL ===================

    private void initSampleData() {
        flights.add(new Flight("GA-101", "Jakarta",  "Bali",       "2026-08-01",  50, 1_500_000.0));
        flights.add(new Flight("QG-201", "Jakarta",  "Surabaya",   "2026-08-01",  80,   800_000.0));
        flights.add(new Flight("ID-301", "Bali",     "Lombok",     "2026-08-02",  30,   600_000.0));
        flights.add(new Flight("GA-401", "Surabaya", "Makassar",   "2026-08-03",  60, 1_200_000.0));
        flights.add(new Flight("SJ-501", "Jakarta",  "Yogyakarta", "2026-08-01", 100,   700_000.0));

        hotels.add(new Hotel("HTL-001", "Hotel Mulia Jakarta",    "Jakarta",    "2026-08-01", "2026-08-03", 10,   800_000.0));
        hotels.add(new Hotel("HTL-002", "Potato Head Beach Club", "Bali",       "2026-08-01", "2026-08-04",  5, 2_500_000.0));
        hotels.add(new Hotel("HTL-003", "Sheraton Surabaya",      "Surabaya",   "2026-08-01", "2026-08-02", 15,   600_000.0));
        hotels.add(new Hotel("HTL-004", "Tentrem Yogyakarta",     "Yogyakarta", "2026-08-01", "2026-08-03",  8, 1_100_000.0));
        hotels.add(new Hotel("HTL-005", "Aston Makassar",         "Makassar",   "2026-08-01", "2026-08-03", 20,   500_000.0));
    }

    // =================== FITUR 1: PENERBANGAN ===================

    public void searchAndBookFlight(Scanner scanner) {
        // 1. Kumpulkan kriteria pencarian dari pengguna
        System.out.print("Kota asal           : ");
        String origin = scanner.nextLine();
        System.out.print("Kota tujuan         : ");
        String destination = scanner.nextLine();
        System.out.print("Tanggal (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        // nextInt() bisa melempar InputMismatchException jika input bukan angka
        int passengerCount;
        try {
            System.out.print("Jumlah penumpang    : ");
            passengerCount = scanner.nextInt();
            scanner.nextLine(); // buang sisa newline agar nextLine() berikutnya tidak terbaca kosong
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Jumlah penumpang harus berupa angka.");
            scanner.nextLine();
            return;
        }

        // 2. Cari penerbangan yang cocok dengan kriteria
        List<Flight> results = searchFlights(origin, destination, date, passengerCount);
        if (results.isEmpty()) {
            System.out.println("Tidak ada penerbangan tersedia.");
            return;
        }

        // 3. Tampilkan hasil dengan nomor urut agar pengguna bisa memilih
        System.out.println("\nHasil pencarian:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        // 4. Pengguna memilih penerbangan berdasarkan nomor urut
        int choice;
        try {
            System.out.print("Pilih penerbangan (1-" + results.size() + "), atau 0 untuk batal: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid.");
            scanner.nextLine();
            return;
        }

        if (choice == 0) return; // pengguna batal
        if (choice < 1 || choice > results.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        // 5. Kumpulkan data pemesan lalu proses pemesanan
        System.out.print("Nama penumpang : ");
        String customerName = scanner.nextLine();
        System.out.print("Kontak         : ");
        String customerContact = scanner.nextLine();

        bookFlight(results.get(choice - 1), passengerCount, customerName, customerContact);
    }

    /**
     * Memfilter daftar penerbangan berdasarkan rute, tanggal, dan ketersediaan kursi.
     *
     * @param origin         kota asal (case-insensitive)
     * @param destination    kota tujuan (case-insensitive)
     * @param date           tanggal (YYYY-MM-DD)
     * @param passengerCount jumlah kursi yang dibutuhkan
     * @return list penerbangan yang memenuhi semua kriteria
     */
    public List<Flight> searchFlights(String origin, String destination,
                                      String date, int passengerCount) {
        return flights.stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin))           // cocokkan kota asal
                .filter(f -> f.getDestination().equalsIgnoreCase(destination)) // cocokkan kota tujuan
                .filter(f -> f.getDate().equals(date))                         // tanggal harus exact match
                .filter(f -> f.getAvailableSeats() >= passengerCount)          // pastikan kursi cukup
                .collect(Collectors.toList());
    }

    /**
     * Membuat {@link FlightReservation}, mengonfirmasi pemesanan, dan menyimpannya ke daftar reservasi.
     *
     * @param flight          penerbangan yang dipilih
     * @param passengerCount  jumlah penumpang
     * @param customerName    nama pemesan
     * @param customerContact kontak pemesan
     */
    public void bookFlight(Flight flight, int passengerCount,
                           String customerName, String customerContact) {
        // Buat objek reservasi dengan data penerbangan dan pemesan
        FlightReservation reservation = new FlightReservation(flight, passengerCount, customerName, customerContact);
        // Generate nomor konfirmasi dan kurangi kursi tersedia
        reservation.book();
        // Simpan ke daftar agar bisa dilihat dan dibatalkan nanti
        reservations.add(reservation);
        // Tampilkan kotak konfirmasi ke pengguna
        reservation.display();
    }

    // =================== FITUR 2: HOTEL ===================

    public void searchAndBookHotel(Scanner scanner) {
        // 1. Kumpulkan kriteria pencarian dari pengguna
        System.out.print("Kota                   : ");
        String location = scanner.nextLine();
        System.out.print("Tanggal check-in  (YYYY-MM-DD): ");
        String checkIn = scanner.nextLine();
        System.out.print("Tanggal check-out (YYYY-MM-DD): ");
        String checkOut = scanner.nextLine();

        int guestCount;
        try {
            System.out.print("Jumlah tamu            : ");
            guestCount = scanner.nextInt();
            scanner.nextLine(); // buang sisa newline
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Jumlah tamu harus berupa angka.");
            scanner.nextLine();
            return;
        }

        // 2. Cari hotel yang cocok dengan kriteria
        List<Hotel> results = searchHotels(location, checkIn, checkOut, guestCount);
        if (results.isEmpty()) {
            System.out.println("Tidak ada hotel tersedia.");
            return;
        }

        // 3. Tampilkan hasil dengan nomor urut
        System.out.println("\nHasil pencarian:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

        // 4. Pengguna memilih hotel
        int choice;
        try {
            System.out.print("Pilih hotel (1-" + results.size() + "), atau 0 untuk batal: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid.");
            scanner.nextLine();
            return;
        }

        if (choice == 0) return; // pengguna batal
        if (choice < 1 || choice > results.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        // 5. Kumpulkan data tamu lalu proses pemesanan
        System.out.print("Nama tamu  : ");
        String customerName = scanner.nextLine();
        System.out.print("Kontak     : ");
        String customerContact = scanner.nextLine();

        bookHotel(results.get(choice - 1), guestCount, customerName, customerContact);
    }

    /**
     * Memfilter daftar hotel berdasarkan lokasi, tanggal, dan ketersediaan kamar.
     * Kecocokan tanggal menggunakan exact match terhadap data inventori.
     *
     * @param location  kota hotel (case-insensitive)
     * @param checkIn   tanggal check-in (YYYY-MM-DD)
     * @param checkOut  tanggal check-out (YYYY-MM-DD)
     * @param guestCount jumlah tamu (tidak difilter, hanya diteruskan ke bookHotel)
     * @return list hotel yang memenuhi semua kriteria
     */
    public List<Hotel> searchHotels(String location, String checkIn,
                                    String checkOut, int guestCount) {
        return hotels.stream()
                .filter(h -> h.getLocation().equalsIgnoreCase(location))  // cocokkan kota
                .filter(h -> h.getCheckInDate().equals(checkIn))          // tanggal check-in exact match
                .filter(h -> h.getCheckOutDate().equals(checkOut))        // tanggal check-out exact match
                .filter(h -> h.getAvailableRooms() > 0)                   // hanya tampilkan yang masih ada kamar
                .collect(Collectors.toList());
    }

    /**
     * Membuat {@link HotelReservation}, mengonfirmasi pemesanan, dan menyimpannya ke daftar reservasi.
     *
     * @param hotel           hotel yang dipilih
     * @param guestCount      jumlah tamu
     * @param customerName    nama pemesan
     * @param customerContact kontak pemesan
     */
    public void bookHotel(Hotel hotel, int guestCount,
                          String customerName, String customerContact) {
        // Buat objek reservasi dengan data hotel dan pemesan
        HotelReservation reservation = new HotelReservation(hotel, guestCount, customerName, customerContact);
        // Generate nomor konfirmasi dan kurangi 1 kamar tersedia
        reservation.book();
        // Simpan ke daftar agar bisa dilihat dan dibatalkan nanti
        reservations.add(reservation);
        // Tampilkan kotak konfirmasi ke pengguna
        reservation.display();
    }

    // =================== FITUR 3: PEMBATALAN ===================

    public void promptCancelReservation(Scanner scanner) {
        System.out.print("Masukkan nomor konfirmasi: ");
        try {
            int confirmationNumber = scanner.nextInt();
            scanner.nextLine();
            // Delegasikan pencarian dan pembatalan ke cancelReservation()
            cancelReservation(confirmationNumber);
            System.out.println("Reservasi berhasil dibatalkan.");
        } catch (ReservationNotFoundException e) {
            // Nomor tidak ditemukan — tampilkan pesan dari exception
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            // Pengguna mengetik teks, bukan angka
            System.out.println("Nomor konfirmasi harus berupa angka.");
            scanner.nextLine();
        }
    }

    /**
     * Mencari reservasi berdasarkan nomor konfirmasi, membatalkannya, dan menghapusnya dari daftar.
     * Menggunakan pattern matching (instanceof) untuk memanggil cancel() yang tepat.
     *
     * @param confirmationNumber nomor konfirmasi yang ingin dibatalkan
     * @throws ReservationNotFoundException jika nomor konfirmasi tidak ditemukan
     */
    public void cancelReservation(int confirmationNumber)
            throws ReservationNotFoundException {
        for (Reservation res : reservations) {
            if (res.getConfirmationNumber() == confirmationNumber) {
                // Pattern matching: tentukan tipe reservasi lalu panggil cancel() yang sesuai
                if (res instanceof FlightReservation fr) {
                    fr.cancel(); // kembalikan kursi ke inventori penerbangan
                } else if (res instanceof HotelReservation hr) {
                    hr.cancel(); // kembalikan kamar ke inventori hotel
                }
                reservations.remove(res); // hapus dari daftar aktif setelah dibatalkan
                return;
            }
        }
        // Sampai di sini berarti tidak ada reservasi dengan nomor tersebut
        throw new ReservationNotFoundException(confirmationNumber);
    }

    // =================== FITUR 4: LIHAT SEMUA PEMESANAN ===================

    /**
     * Menampilkan semua reservasi aktif. Memanfaatkan polimorfisme: setiap elemen
     * memanggil display() versinya masing-masing (FlightReservation atau HotelReservation).
     */
    public void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }
        System.out.println("=== DAFTAR PEMESANAN ANDA ===");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println("\n--- Reservasi ke-" + (i + 1) + " ---");
            // display() otomatis memanggil versi FlightReservation atau HotelReservation (polimorfisme)
            reservations.get(i).display();
        }
    }

    // =================== HELPER ===================

    /** Mencetak garis pemisah dekoratif untuk UI konsol. */
    public void printSeparator() {
        System.out.println("════════════════════════════════════════════");
    }
}
