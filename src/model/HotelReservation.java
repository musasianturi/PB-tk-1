package model;

import util.ConfirmationNumberGenerator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Reservasi kamar hotel. Menyimpan referensi ke {@link Hotel} yang dipesan
 * beserta data tamu. Satu reservasi selalu memesan tepat 1 kamar.
 */
public final class HotelReservation extends Reservation {

    private Hotel hotel;
    private int guestCount;

    /**
     * @param hotel           hotel yang dipesan
     * @param guestCount      jumlah tamu
     * @param customerName    nama pemesan
     * @param customerContact kontak pemesan (telepon/email)
     */
    public HotelReservation(Hotel hotel, int guestCount,
                            String customerName, String customerContact) {
        this.hotel           = hotel;
        this.guestCount      = guestCount;
        this.customerName    = customerName;
        this.customerContact = customerContact;
    }

    /**
     * Mengonfirmasi pemesanan: membuat nomor konfirmasi dan mengurangi 1 kamar tersedia.
     * Dipanggil satu kali oleh {@link app.TravelApp#bookHotel} setelah pengguna memilih.
     */
    @Override
    public void book() {
        // Buat nomor konfirmasi unik 6 digit untuk reservasi ini
        this.confirmationNumber = ConfirmationNumberGenerator.generate();
        // Setiap pemesanan hotel selalu mengambil tepat 1 kamar, terlepas dari jumlah tamu
        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        System.out.println("Pemesanan berhasil! No. Konfirmasi: " + confirmationNumber);
    }

    /** Membatalkan pemesanan dan mengembalikan 1 kamar ke inventori hotel. */
    @Override
    public void cancel() {
        // Kembalikan kamar yang sebelumnya dikurangi saat book()
        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
        System.out.println("Pemesanan hotel " + hotel.getName() + " dibatalkan.");
    }

    /** Mencetak ringkasan konfirmasi pemesanan hotel ke konsol. Total harga dihitung dari jumlah malam. */
    @Override
    public void display() {
        // Parse string tanggal ke LocalDate agar bisa dihitung selisihnya
        LocalDate checkIn  = LocalDate.parse(hotel.getCheckInDate());
        LocalDate checkOut = LocalDate.parse(hotel.getCheckOutDate());
        // Hitung jumlah malam menginap sebagai dasar perhitungan total harga
        long nights       = ChronoUnit.DAYS.between(checkIn, checkOut);
        double totalHarga = nights * hotel.getPricePerNight();
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
