package model;

import util.ConfirmationNumberGenerator;

/**
 * Reservasi tiket penerbangan. Menyimpan referensi ke {@link Flight} yang dipesan
 * beserta data penumpang. Satu reservasi mewakili satu transaksi pembelian tiket.
 */
public final class FlightReservation extends Reservation {

    private Flight flight;
    private int passengerCount;

    /**
     * @param flight          penerbangan yang dipesan
     * @param passengerCount  jumlah penumpang
     * @param customerName    nama pemesan
     * @param customerContact kontak pemesan (telepon/email)
     */
    public FlightReservation(Flight flight, int passengerCount,
                             String customerName, String customerContact) {
        this.flight          = flight;
        this.passengerCount  = passengerCount;
        this.customerName    = customerName;
        this.customerContact = customerContact;
    }

    /**
     * Mengonfirmasi pemesanan: membuat nomor konfirmasi dan mengurangi kursi tersedia.
     * Dipanggil satu kali oleh {@link app.TravelApp#bookFlight} setelah pengguna memilih.
     */
    @Override
    public void book() {
        // Buat nomor konfirmasi unik 6 digit untuk reservasi ini
        this.confirmationNumber = ConfirmationNumberGenerator.generate();
        // Kurangi kursi tersedia sesuai jumlah penumpang yang dipesan
        flight.setAvailableSeats(flight.getAvailableSeats() - passengerCount);
        System.out.println("Pemesanan berhasil! No. Konfirmasi: " + confirmationNumber);
    }

    /** Membatalkan pemesanan dan mengembalikan kursi ke inventori penerbangan. */
    @Override
    public void cancel() {
        // Kembalikan kursi yang sebelumnya dikurangi saat book()
        flight.setAvailableSeats(flight.getAvailableSeats() + passengerCount);
        System.out.println("Pemesanan penerbangan " + flight.getFlightNumber() + " dibatalkan.");
    }

    /** Mencetak ringkasan konfirmasi pemesanan penerbangan ke konsol. */
    @Override
    public void display() {
        // Hitung total harga berdasarkan jumlah penumpang dan harga per kursi
        double totalHarga = passengerCount * flight.getPricePerSeat();
        System.out.println("┌────────────────────────────────────────────┐");
        System.out.println("│      KONFIRMASI PEMESANAN PENERBANGAN      │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.printf( "│ No. Konfirmasi : %-27d│%n", confirmationNumber);
        System.out.printf( "│ Nama Penumpang : %-27s│%n", customerName);
        System.out.printf( "│ Kontak         : %-27s│%n", customerContact);
        System.out.printf( "│ Penerbangan    : %-27s│%n", flight.getFlightNumber());
        System.out.printf( "│ Rute           : %-27s│%n", flight.getOrigin() + " → " + flight.getDestination());
        System.out.printf( "│ Tanggal        : %-27s│%n", flight.getDate());
        System.out.printf( "│ Jumlah Tiket   : %-27d│%n", passengerCount);
        System.out.printf( "│ Total Harga    : %-27s│%n", String.format("Rp %,.0f", totalHarga));
        System.out.println("└────────────────────────────────────────────┘");
    }

    public Flight getFlight()       { return flight; }
    public int getPassengerCount()  { return passengerCount; }
}
