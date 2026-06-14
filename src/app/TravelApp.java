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
        System.out.print("Kota asal           : ");
        String origin = scanner.nextLine();
        System.out.print("Kota tujuan         : ");
        String destination = scanner.nextLine();
        System.out.print("Tanggal (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        int passengerCount;
        try {
            System.out.print("Jumlah penumpang    : ");
            passengerCount = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Jumlah penumpang harus berupa angka.");
            scanner.nextLine();
            return;
        }

        List<Flight> results = searchFlights(origin, destination, date, passengerCount);
        if (results.isEmpty()) {
            System.out.println("Tidak ada penerbangan tersedia.");
            return;
        }

        System.out.println("\nHasil pencarian:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

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

        if (choice == 0) return;
        if (choice < 1 || choice > results.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        System.out.print("Nama penumpang : ");
        String customerName = scanner.nextLine();
        System.out.print("Kontak         : ");
        String customerContact = scanner.nextLine();

        bookFlight(results.get(choice - 1), passengerCount, customerName, customerContact);
    }

    public List<Flight> searchFlights(String origin, String destination,
                                      String date, int passengerCount) {
        return flights.stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin))
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> f.getDate().equals(date))
                .filter(f -> f.getAvailableSeats() >= passengerCount)
                .collect(Collectors.toList());
    }


    public void bookFlight(Flight flight, int passengerCount,
                           String customerName, String customerContact) {
        FlightReservation reservation = new FlightReservation(flight, passengerCount, customerName, customerContact);
        reservation.book();
        reservations.add(reservation);
        reservation.display();
    }

    // =================== FITUR 2: HOTEL ===================


    public void searchAndBookHotel(Scanner scanner) {
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
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Jumlah tamu harus berupa angka.");
            scanner.nextLine();
            return;
        }

        List<Hotel> results = searchHotels(location, checkIn, checkOut, guestCount);
        if (results.isEmpty()) {
            System.out.println("Tidak ada hotel tersedia.");
            return;
        }

        System.out.println("\nHasil pencarian:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ". " + results.get(i));
        }

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

        if (choice == 0) return;
        if (choice < 1 || choice > results.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        System.out.print("Nama tamu  : ");
        String customerName = scanner.nextLine();
        System.out.print("Kontak     : ");
        String customerContact = scanner.nextLine();

        bookHotel(results.get(choice - 1), guestCount, customerName, customerContact);
    }


    public List<Hotel> searchHotels(String location, String checkIn,
                                    String checkOut, int guestCount) {
        return hotels.stream()
                .filter(h -> h.getLocation().equalsIgnoreCase(location))
                .filter(h -> h.getCheckInDate().equals(checkIn))
                .filter(h -> h.getCheckOutDate().equals(checkOut))
                .filter(h -> h.getAvailableRooms() > 0)
                .collect(Collectors.toList());
    }


    public void bookHotel(Hotel hotel, int guestCount,
                          String customerName, String customerContact) {
        HotelReservation reservation = new HotelReservation(hotel, guestCount, customerName, customerContact);
        reservation.book();
        reservations.add(reservation);
        reservation.display();
    }

    // =================== FITUR 3: PEMBATALAN ===================

    public void promptCancelReservation(Scanner scanner) {
        System.out.print("Masukkan nomor konfirmasi: ");
        try {
            int confirmationNumber = scanner.nextInt();
            scanner.nextLine();
            cancelReservation(confirmationNumber);
            System.out.println("Reservasi berhasil dibatalkan.");
        } catch (ReservationNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Nomor konfirmasi harus berupa angka.");
            scanner.nextLine();
        }
    }


    public void cancelReservation(int confirmationNumber)
            throws ReservationNotFoundException {
        for (Reservation res : reservations) {
            if (res.getConfirmationNumber() == confirmationNumber) {
                if (res instanceof FlightReservation fr) {
                    fr.cancel();
                } else if (res instanceof HotelReservation hr) {
                    hr.cancel();
                }
                reservations.remove(res);
                return;
            }
        }
        throw new ReservationNotFoundException(confirmationNumber);
    }


    public void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }
        System.out.println("=== DAFTAR PEMESANAN ANDA ===");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println("\n--- Reservasi ke-" + (i + 1) + " ---");
            reservations.get(i).display();
        }
    }

    // =================== HELPER ===================

    public void printSeparator() {
        System.out.println("════════════════════════════════════════════");
    }
}
