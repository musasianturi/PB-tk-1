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
        // TODO [ANGGOTA 2]: assign semua parameter ke fields
        // this.flight          = flight;
        // this.passengerCount  = passengerCount;
        // this.customerName    = customerName;    ← field dari Reservation
        // this.customerContact = customerContact; ← field dari Reservation
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
        // TODO [ANGGOTA 2]: implementasikan langkah-langkah di atas
        System.out.println("[TODO] FlightReservation.book() belum diimplementasikan");
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
        // TODO [ANGGOTA 2]: implementasikan langkah-langkah di atas
        System.out.println("[TODO] FlightReservation.cancel() belum diimplementasikan");
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
        // TODO [ANGGOTA 2]: implementasikan tampilan di atas dengan System.out.println()
        System.out.println("[TODO] FlightReservation.display() belum diimplementasikan");
        System.out.println("  Konfirmasi: " + confirmationNumber
                + " | Penumpang: " + customerName);
    }

    // =================== GETTERS ===================

    /**
     * TODO [ANGGOTA 2]: return nilai field flight
     */
    public Flight getFlight() {
        return null; // TODO: return flight;
    }

    /**
     * TODO [ANGGOTA 2]: return nilai field passengerCount
     */
    public int getPassengerCount() {
        return 0; // TODO: return passengerCount;
    }
}
