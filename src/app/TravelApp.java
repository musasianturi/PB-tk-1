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
 * TravelApp adalah kelas utama yang mengandung seluruh logika bisnis aplikasi.
 * Kelas ini mengelola koleksi data dan menyediakan method untuk setiap fitur menu.
 *
 * KONSEP JAVA — ArrayList & Koleksi:
 *   ArrayList adalah implementasi List yang dinamis (bisa tambah/hapus elemen).
 *   Kita gunakan tiga ArrayList:
 *     - flights    : menyimpan semua penerbangan yang tersedia (inventori)
 *     - hotels     : menyimpan semua hotel yang tersedia (inventori)
 *     - reservations: menyimpan semua pemesanan aktif pengguna
 *
 * KONSEP JAVA — Stream API & Lambda:
 *   Stream memungkinkan pemrosesan koleksi secara deklaratif dan fungsional.
 *   Lambda (f -> f.getDestination().equals("Bali")) adalah anonymous function singkat.
 *   Kombinasi stream + filter + collect menggantikan loop manual yang verbose.
 *
 * KONSEP JAVA — Exception Handling:
 *   try-catch digunakan untuk menangkap:
 *     - InputMismatchException: ketika pengguna memasukkan teks di kolom angka
 *     - ReservationNotFoundException: ketika nomor konfirmasi tidak ditemukan
 *
 * TODO [ANGGOTA 3] — Implementasikan semua method yang ditandai TODO.
 *   Urutan pengerjaan yang disarankan:
 *   1. initSampleData()     ← tambahkan data dummy ke flights dan hotels
 *   2. searchFlights()      ← filter koleksi flights dengan stream + lambda
 *   3. bookFlight()         ← buat FlightReservation, panggil book(), simpan ke reservations
 *   4. searchHotels()       ← filter koleksi hotels dengan stream + lambda
 *   5. bookHotel()          ← buat HotelReservation, panggil book(), simpan ke reservations
 *   6. cancelReservation()  ← cari di reservations, pattern matching, hapus, throw jika tidak ada
 *   7. viewAllReservations() ← iterasi reservations, panggil display() tiap elemen
 */
public class TravelApp {

    // =================== KOLEKSI DATA ===================

    /** Inventori semua penerbangan yang tersedia di sistem */
    private final ArrayList<Flight> flights = new ArrayList<>();

    /** Inventori semua hotel yang tersedia di sistem */
    private final ArrayList<Hotel> hotels = new ArrayList<>();

    /** Daftar semua reservasi (penerbangan + hotel) yang sudah dipesan */
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    // =================== CONSTRUCTOR ===================

    /**
     * Inisialisasi TravelApp dan muat data awal.
     */
    public TravelApp() {
        initSampleData();
    }

    // =================== INISIALISASI DATA ===================

    /**
     * Isi koleksi flights dan hotels dengan data contoh.
     *
     * TODO [ANGGOTA 3]:
     *   Tambahkan minimal 5 penerbangan ke ArrayList flights menggunakan flights.add(new Flight(...))
     *   Tambahkan minimal 5 hotel ke ArrayList hotels menggunakan hotels.add(new Hotel(...))
     *
     *   Contoh penerbangan:
     *     flights.add(new Flight("GA-101", "Jakarta",  "Bali",      "2025-08-01", 50,  1_500_000.0));
     *     flights.add(new Flight("QG-201", "Jakarta",  "Surabaya",  "2025-08-01", 80,    800_000.0));
     *     flights.add(new Flight("ID-301", "Bali",     "Lombok",    "2025-08-02", 30,    600_000.0));
     *     flights.add(new Flight("GA-401", "Surabaya", "Makassar",  "2025-08-03", 60,  1_200_000.0));
     *     flights.add(new Flight("SJ-501", "Jakarta",  "Yogyakarta","2025-08-01", 100,   700_000.0));
     *
     *   Contoh hotel (checkInDate dan checkOutDate bisa sama untuk inventori umum,
     *   atau dibuat tanpa tanggal — sesuaikan dengan desain tim):
     *     hotels.add(new Hotel("HTL-001", "Hotel Mulia Jakarta",    "Jakarta", "2025-08-01", "2025-08-03", 10, 800_000.0));
     *     hotels.add(new Hotel("HTL-002", "Potato Head Beach Club", "Bali",    "2025-08-01", "2025-08-04",  5, 2_500_000.0));
     *     hotels.add(new Hotel("HTL-003", "Sheraton Surabaya",      "Surabaya","2025-08-01", "2025-08-02", 15,   600_000.0));
     *     hotels.add(new Hotel("HTL-004", "Tentrem Yogyakarta",     "Yogyakarta","2025-08-01","2025-08-03",  8, 1_100_000.0));
     *     hotels.add(new Hotel("HTL-005", "Aston Makassar",         "Makassar","2025-08-01", "2025-08-03", 20,   500_000.0));
     */
    private void initSampleData() {
        flights.add(new Flight("GA-101", "Jakarta",  "Bali",       "2025-08-01",  50, 1_500_000.0));
        flights.add(new Flight("QG-201", "Jakarta",  "Surabaya",   "2025-08-01",  80,   800_000.0));
        flights.add(new Flight("ID-301", "Bali",     "Lombok",     "2025-08-02",  30,   600_000.0));
        flights.add(new Flight("GA-401", "Surabaya", "Makassar",   "2025-08-03",  60, 1_200_000.0));
        flights.add(new Flight("SJ-501", "Jakarta",  "Yogyakarta", "2025-08-01", 100,   700_000.0));

        hotels.add(new Hotel("HTL-001", "Hotel Mulia Jakarta",    "Jakarta",    "2025-08-01", "2025-08-03", 10,   800_000.0));
        hotels.add(new Hotel("HTL-002", "Potato Head Beach Club", "Bali",       "2025-08-01", "2025-08-04",  5, 2_500_000.0));
        hotels.add(new Hotel("HTL-003", "Sheraton Surabaya",      "Surabaya",   "2025-08-01", "2025-08-02", 15,   600_000.0));
        hotels.add(new Hotel("HTL-004", "Tentrem Yogyakarta",     "Yogyakarta", "2025-08-01", "2025-08-03",  8, 1_100_000.0));
        hotels.add(new Hotel("HTL-005", "Aston Makassar",         "Makassar",   "2025-08-01", "2025-08-03", 20,   500_000.0));
    }

    // =================== FITUR 1: PENCARIAN & PEMESANAN PENERBANGAN ===================

    /**
     * Alur lengkap pencarian dan pemesanan penerbangan.
     * Method ini dipanggil dari Main.java saat pengguna memilih menu "Cari Penerbangan".
     *
     * TODO [ANGGOTA 3]:
     *   Langkah 1 — Minta input dari pengguna:
     *     System.out.print("Kota asal: ");
     *     String origin = scanner.nextLine();
     *     // ulangi untuk destination, date, passengerCount
     *     // PENTING: tangkap InputMismatchException saat baca angka dengan try-catch!
     *
     *   Langkah 2 — Cari penerbangan yang tersedia:
     *     List<Flight> results = searchFlights(origin, destination, date, passengerCount);
     *
     *   Langkah 3 — Tampilkan hasil atau pesan tidak ditemukan:
     *     if (results.isEmpty()) {
     *         System.out.println("Tidak ada penerbangan tersedia.");
     *         return;
     *     }
     *     for (int i = 0; i < results.size(); i++) {
     *         System.out.println((i + 1) + ". " + results.get(i));
     *     }
     *
     *   Langkah 4 — Minta pengguna pilih penerbangan (atau kembali ke menu):
     *     System.out.print("Pilih penerbangan (1-" + results.size() + "), atau 0 untuk batal: ");
     *
     *   Langkah 5 — Minta data penumpang dan panggil bookFlight():
     *     // Minta customerName dan customerContact
     *     bookFlight(selectedFlight, passengerCount, customerName, customerContact);
     *
     * @param scanner Scanner untuk membaca input pengguna
     */
    public void searchAndBookFlight(Scanner scanner) {
        // TODO [ANGGOTA 3]: implementasikan alur di atas
        System.out.println("[TODO] searchAndBookFlight() belum diimplementasikan");
    }

    /**
     * Filter daftar penerbangan berdasarkan kriteria pencarian.
     *
     * TODO [ANGGOTA 3]:
     *   Gunakan Stream API + Lambda untuk memfilter ArrayList flights.
     *   Setiap kondisi filter ditambahkan dengan .filter():
     *
     *   return flights.stream()
     *       .filter(f -> f.getOrigin().equalsIgnoreCase(origin))
     *       .filter(f -> f.getDestination().equalsIgnoreCase(destination))
     *       .filter(f -> f.getDate().equals(date))
     *       .filter(f -> f.getAvailableSeats() >= passengerCount)
     *       .collect(Collectors.toList());
     *
     *   KONSEP: equalsIgnoreCase() agar "jakarta" == "Jakarta" == "JAKARTA"
     *
     * @param origin          kota asal
     * @param destination     kota tujuan
     * @param date            tanggal (format YYYY-MM-DD)
     * @param passengerCount  jumlah penumpang (harus <= availableSeats)
     * @return daftar penerbangan yang memenuhi semua kriteria
     */
    public List<Flight> searchFlights(String origin, String destination,
                                      String date, int passengerCount) {
        // TODO [ANGGOTA 3]: gunakan flights.stream().filter(...).collect(Collectors.toList())
        return new ArrayList<>(); // placeholder — hapus dan ganti dengan implementasi
    }

    /**
     * Buat dan simpan reservasi penerbangan baru.
     *
     * TODO [ANGGOTA 3]:
     *   Langkah 1: Buat objek FlightReservation:
     *     FlightReservation reservation = new FlightReservation(
     *         flight, passengerCount, customerName, customerContact);
     *
     *   Langkah 2: Konfirmasi pemesanan (generate no. konfirmasi + kurangi kursi):
     *     reservation.book();
     *
     *   Langkah 3: Simpan ke daftar reservasi:
     *     reservations.add(reservation);
     *
     *   Langkah 4: Tampilkan detail konfirmasi:
     *     reservation.display();
     *
     * @param flight          penerbangan yang dipilih pengguna
     * @param passengerCount  jumlah penumpang
     * @param customerName    nama pemesan
     * @param customerContact kontak pemesan
     */
    public void bookFlight(Flight flight, int passengerCount,
                           String customerName, String customerContact) {
        // TODO [ANGGOTA 3]: implementasikan langkah-langkah di atas
        System.out.println("[TODO] bookFlight() belum diimplementasikan");
    }

    // =================== FITUR 2: PENCARIAN & PEMESANAN HOTEL ===================

    /**
     * Alur lengkap pencarian dan pemesanan hotel.
     * Method ini dipanggil dari Main.java saat pengguna memilih menu "Cari Hotel".
     *
     * TODO [ANGGOTA 3]:
     *   Langkah 1 — Minta input pengguna:
     *     Kota (location), check-in date, check-out date, jumlah tamu (guestCount)
     *     Tangkap InputMismatchException untuk input angka.
     *
     *   Langkah 2 — Cari hotel:
     *     List<Hotel> results = searchHotels(location, checkIn, checkOut, guestCount);
     *
     *   Langkah 3 — Tampilkan hasil (mirip alur penerbangan):
     *     Tampilkan list bernomor, atau pesan "Tidak ada hotel tersedia."
     *
     *   Langkah 4 — Pengguna pilih hotel berdasarkan nomor urut.
     *
     *   Langkah 5 — Minta data tamu dan panggil bookHotel():
     *     bookHotel(selectedHotel, guestCount, customerName, customerContact);
     *
     * @param scanner Scanner untuk membaca input pengguna
     */
    public void searchAndBookHotel(Scanner scanner) {
        // TODO [ANGGOTA 3]: implementasikan alur di atas
        System.out.println("[TODO] searchAndBookHotel() belum diimplementasikan");
    }

    /**
     * Filter daftar hotel berdasarkan kriteria pencarian.
     *
     * TODO [ANGGOTA 3]:
     *   Gunakan Stream API, mirip searchFlights():
     *
     *   return hotels.stream()
     *       .filter(h -> h.getLocation().equalsIgnoreCase(location))
     *       .filter(h -> h.getAvailableRooms() > 0)
     *       // Filter tanggal bisa disederhanakan: cek apakah hotel tersedia di rentang tanggal.
     *       // Untuk simplifikasi: filter h.getCheckInDate().equals(checkIn)
     *       //                   && h.getCheckOutDate().equals(checkOut)
     *       .collect(Collectors.toList());
     *
     * @param location   kota hotel
     * @param checkIn    tanggal check-in
     * @param checkOut   tanggal check-out
     * @param guestCount jumlah tamu
     * @return daftar hotel yang memenuhi kriteria
     */
    public List<Hotel> searchHotels(String location, String checkIn,
                                    String checkOut, int guestCount) {
        // TODO [ANGGOTA 3]: gunakan hotels.stream().filter(...).collect(Collectors.toList())
        return new ArrayList<>(); // placeholder — hapus dan ganti dengan implementasi
    }

    /**
     * Buat dan simpan reservasi hotel baru.
     *
     * TODO [ANGGOTA 3]:
     *   Langkah 1: Buat objek HotelReservation:
     *     HotelReservation reservation = new HotelReservation(
     *         hotel, guestCount, customerName, customerContact);
     *
     *   Langkah 2: Konfirmasi pemesanan:
     *     reservation.book();
     *
     *   Langkah 3: Simpan ke reservations:
     *     reservations.add(reservation);
     *
     *   Langkah 4: Tampilkan detail:
     *     reservation.display();
     *
     * @param hotel           hotel yang dipilih pengguna
     * @param guestCount      jumlah tamu
     * @param customerName    nama pemesan
     * @param customerContact kontak pemesan
     */
    public void bookHotel(Hotel hotel, int guestCount,
                          String customerName, String customerContact) {
        // TODO [ANGGOTA 3]: implementasikan langkah-langkah di atas
        System.out.println("[TODO] bookHotel() belum diimplementasikan");
    }

    // =================== FITUR 3: PEMBATALAN RESERVASI ===================

    /**
     * Alur pembatalan reservasi berdasarkan nomor konfirmasi.
     * Method ini dipanggil dari Main.java saat pengguna memilih menu "Batalkan Reservasi".
     *
     * TODO [ANGGOTA 3]:
     *   Langkah 1: Minta input nomor konfirmasi:
     *     System.out.print("Masukkan nomor konfirmasi: ");
     *     int confirmationNumber = scanner.nextInt();
     *     scanner.nextLine(); // flush newline
     *
     *   Langkah 2: Panggil cancelReservation() dalam try-catch:
     *     try {
     *         cancelReservation(confirmationNumber);
     *         System.out.println("Reservasi berhasil dibatalkan.");
     *     } catch (ReservationNotFoundException e) {
     *         System.out.println(e.getMessage());
     *     } catch (InputMismatchException e) {
     *         System.out.println("Nomor konfirmasi harus berupa angka.");
     *         scanner.nextLine();
     *     }
     *
     * @param scanner Scanner untuk membaca input pengguna
     */
    public void promptCancelReservation(Scanner scanner) {
        // TODO [ANGGOTA 3]: implementasikan alur di atas
        System.out.println("[TODO] promptCancelReservation() belum diimplementasikan");
    }

    /**
     * Cari dan batalkan reservasi berdasarkan nomor konfirmasi.
     *
     * TODO [ANGGOTA 3]:
     *   Gunakan enhanced for loop untuk iterasi reservations:
     *
     *   for (Reservation res : reservations) {
     *       if (res.getConfirmationNumber() == confirmationNumber) {
     *
     *           // PATTERN MATCHING (instanceof dengan binding) — Java 16+
     *           if (res instanceof FlightReservation fr) {
     *               fr.cancel();  // 'fr' sudah di-cast ke FlightReservation, tidak perlu cast manual
     *               System.out.println("Pemesanan penerbangan " +
     *                   fr.getFlight().getFlightNumber() + " dibatalkan.");
     *           } else if (res instanceof HotelReservation hr) {
     *               hr.cancel();
     *               System.out.println("Pemesanan hotel " +
     *                   hr.getHotel().getName() + " dibatalkan.");
     *           }
     *
     *           reservations.remove(res);  // hapus dari daftar
     *           return;  // selesai, keluar dari method
     *       }
     *   }
     *
     *   // Jika sampai di sini, reservasi tidak ditemukan → lempar exception
     *   throw new ReservationNotFoundException(confirmationNumber);
     *
     * @param confirmationNumber nomor konfirmasi yang ingin dibatalkan
     * @throws ReservationNotFoundException jika nomor konfirmasi tidak ditemukan
     */
    public void cancelReservation(int confirmationNumber)
            throws ReservationNotFoundException {
        // TODO [ANGGOTA 3]: implementasikan logika di atas
        System.out.println("[TODO] cancelReservation() belum diimplementasikan");
        throw new ReservationNotFoundException(confirmationNumber);
    }

    // =================== FITUR 4: LIHAT SEMUA PEMESANAN ===================

    /**
     * Tampilkan semua reservasi aktif yang ada di sistem.
     *
     * TODO [ANGGOTA 3]:
     *   Langkah 1: Cek apakah reservations kosong:
     *     if (reservations.isEmpty()) {
     *         System.out.println("Belum ada reservasi.");
     *         return;
     *     }
     *
     *   Langkah 2: Iterasi dan tampilkan setiap reservasi:
     *     System.out.println("=== DAFTAR PEMESANAN ANDA ===");
     *     for (int i = 0; i < reservations.size(); i++) {
     *         System.out.println("\n--- Reservasi ke-" + (i + 1) + " ---");
     *         reservations.get(i).display();  // polimorfisme: display() yang dipanggil
     *     }                                   // sesuai tipe (Flight/Hotel)
     *
     *   Atau gunakan lambda dengan forEach:
     *     reservations.forEach(r -> {
     *         System.out.println("---");
     *         r.display();
     *     });
     */
    public void viewAllReservations() {
        // TODO [ANGGOTA 3]: implementasikan tampilan semua reservasi
        System.out.println("[TODO] viewAllReservations() belum diimplementasikan");
        System.out.println("Jumlah reservasi saat ini: " + reservations.size());
    }

    // =================== HELPER: TAMPILKAN SEPARATOR ===================

    /**
     * Tampilkan garis pemisah untuk estetika UI konsol.
     * Method ini sudah selesai dan bisa langsung digunakan.
     */
    public void printSeparator() {
        System.out.println("════════════════════════════════════════════");
    }
}
