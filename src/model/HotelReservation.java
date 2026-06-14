package model;

import util.ConfirmationNumberGenerator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class HotelReservation extends Reservation {

    private Hotel hotel;
    private int guestCount;

    public HotelReservation(Hotel hotel, int guestCount,
                            String customerName, String customerContact) {
        this.hotel           = hotel;
        this.guestCount      = guestCount;
        this.customerName    = customerName;
        this.customerContact = customerContact;
    }


    @Override
    public void book() {
        this.confirmationNumber = ConfirmationNumberGenerator.generate();
        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        System.out.println("Pemesanan berhasil! No. Konfirmasi: " + confirmationNumber);
    }

    @Override
    public void cancel() {
        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        System.out.println("Pemesanan hotel " + hotel.getName() + " dibatalkan.");
    }

    @Override
    public void display() {
        LocalDate checkIn  = LocalDate.parse(hotel.getCheckInDate());
        LocalDate checkOut = LocalDate.parse(hotel.getCheckOutDate());
        long nights        = ChronoUnit.DAYS.between(checkIn, checkOut);
        double totalHarga  = nights * hotel.getPricePerNight();
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│         KONFIRMASI PEMESANAN HOTEL         │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.printf( "│ No. Konfirmasi : %-27d│%n", confirmationNumber);
        System.out.printf( "│ Nama Tamu      : %-27s│%n", customerName);
        System.out.printf( "│ Kontak         : %-27s│%n", customerContact);
        System.out.printf( "│ Hotel          : %-27s│%n", hotel.getName());
        System.out.printf( "│ Lokasi         : %-27s│%n", hotel.getLocation());
        System.out.printf( "│ Check-in       : %-27s│%n", hotel.getCheckInDate());
        System.out.printf( "│ Check-out      : %-27s│%n", hotel.getCheckOutDate());
        System.out.printf( "│ Jumlah Tamu    : %-27d│%n", guestCount);
        System.out.printf( "│ Total Harga    : %-27s│%n", String.format("Rp %,.0f", totalHarga));
        System.out.println("└────────────────────────────────────────────┘");
    }

    public Hotel getHotel()    { return hotel; }
    public int getGuestCount() { return guestCount; }
}
