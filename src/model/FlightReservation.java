package model;

import util.ConfirmationNumberGenerator;

/**
 * FlightReservation merepresentasikan pemesanan tiket penerbangan oleh penumpang.
 * Menyimpan data penerbangan yang dipesan beserta informasi penumpang.
 *
 * KONSEP JAVA — Inheritance:
 *   'extends Reservation' → FlightReservation mewarisi field confirmationNumber,
 *   customerName, customerContact dari Reservation, serta kewajiban implements Bookable.
 *   Gunakan 'super.fieldName' jika perlu akses langsung, atau getter/setter yang diwarisi.
 *
 * KONSEP JAVA — Polymorphism & Method Override:
 *   @Override menandai bahwa method ini menggantikan implementasi di superclass/interface.
 *   Dengan polimorfisme, kode yang memegang referensi Reservation bisa memanggil display()
 *   dan mendapat output spesifik FlightReservation tanpa tahu tipenya.
 *
 * KONSEP JAVA — Pattern Matching (instanceof):
 *   Di TravelApp.cancelReservation(), kode:
 *     if (res instanceof FlightReservation fr) { fr.cancel(); }
 *   mengecek tipe reservasi sekaligus membuat variabel 'fr' yang sudah di-cast.
 *   Ini lebih ringkas dari cara lama: if (res instanceof FlightReservation) { ((FlightReservation)res).cancel(); }
 *
 * TODO [ANGGOTA 2] — Implementasikan semua method yang ditandai TODO.
 */
public final class FlightReservation extends Reservation {

    // Field tambahan spesifik untuk reservasi penerbangan
    private Flight flight;        // objek penerbangan yang dipesan
    private int passengerCount;   // jumlah penumpang (tiket yang dipesan)

    // =================== CONSTRUCTOR ===================

    /**
     * Buat reservasi penerbangan baru.
     *
     * TODO [ANGGOTA 2]:
     *   Assign setiap parameter ke field yang sesuai.
     *   Untuk field yang diwarisi (customerName, customerContact), gunakan:
     *     this.customerName    = customerName;
     *     this.customerContact = customerContact;
     *   (field 'protected' dari superclass bisa diakses langsung di subclass)
     *
     *   CATATAN: Jangan panggil book() di constructor.
     *   book() dipanggil terpisah oleh TravelApp setelah pengguna konfirmasi.
     *
     * @param flight          objek Flight yang dipesan
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

    // =================== OVERRIDE: book() ===================

    /**
     * Konfirmasi pemesanan penerbangan.
     *
     * TODO [ANGGOTA 2]:
     *   Langkah 1: Generate nomor konfirmasi unik:
     *     this.confirmationNumber = ConfirmationNumberGenerator.generate();
     *
     *   Langkah 2: Kurangi kursi tersedia di penerbangan:
     *     int currentSeats = flight.getAvailableSeats();
     *     flight.setAvailableSeats(currentSeats - passengerCount);
     *
     *   Langkah 3: (Opsional) Tampilkan pesan konfirmasi:
     *     System.out.println("Pemesanan berhasil! No. Konfirmasi: " + confirmationNumber);
     */
    @Override
    public void book() {
        this.confirmationNumber = ConfirmationNumberGenerator.generate();
        flight.setAvailableSeats(flight.getAvailableSeats() - passengerCount);
        System.out.println("Pemesanan berhasil! No. Konfirmasi: " + confirmationNumber);
    }

    // =================== OVERRIDE: cancel() ===================

    /**
     * Batalkan pemesanan dan kembalikan kursi ke penerbangan.
     *
     * TODO [ANGGOTA 2]:
     *   Langkah 1: Kembalikan kursi ke penerbangan:
     *     int currentSeats = flight.getAvailableSeats();
     *     flight.setAvailableSeats(currentSeats + passengerCount);
     *
     *   Langkah 2: (Opsional) Tampilkan pesan pembatalan:
     *     System.out.println("Pemesanan penerbangan " + flight.getFlightNumber() + " dibatalkan.");
     */
    @Override
    public void cancel() {
        flight.setAvailableSeats(flight.getAvailableSeats() + passengerCount);
        System.out.println("Pemesanan penerbangan " + flight.getFlightNumber() + " dibatalkan.");
    }

    // =================== OVERRIDE: display() ===================

    /**
     * Tampilkan detail konfirmasi reservasi penerbangan ke konsol.
     *
     * TODO [ANGGOTA 2]:
     *   Tampilkan informasi dalam format yang rapi. Contoh format:
     *
     *   ┌────────────────────────────────────────────┐
     *   │      KONFIRMASI PEMESANAN PENERBANGAN      │
     *   ├────────────────────────────────────────────┤
     *   │ No. Konfirmasi : 472819                    │
     *   │ Nama Penumpang : Budi Santoso              │
     *   │ Kontak         : 08123456789               │
     *   │ Penerbangan    : GA-101                    │
     *   │ Rute           : Jakarta → Bali            │
     *   │ Tanggal        : 2025-08-01                │
     *   │ Jumlah Tiket   : 2                         │
     *   │ Total Harga    : Rp 3.000.000              │
     *   └────────────────────────────────────────────┘
     *
     *   Total harga = passengerCount * flight.getPricePerSeat()
     *   Format harga: String.format("Rp %,.0f", totalHarga)
     */
    @Override
    public void display() {
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

    // =================== GETTERS ===================

    /**
     * TODO [ANGGOTA 2]: return nilai field flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * TODO [ANGGOTA 2]: return nilai field passengerCount
     */
    public int getPassengerCount() {
        return passengerCount;
    }
}
