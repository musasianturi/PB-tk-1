package model;

import util.ConfirmationNumberGenerator;

/**
 * HotelReservation merepresentasikan pemesanan kamar hotel oleh tamu.
 * Menyimpan data hotel yang dipesan beserta informasi tamu.
 *
 * KONSEP JAVA — Inheritance & Polymorphism:
 *   Sama seperti FlightReservation, class ini extends Reservation dan mengimplementasikan
 *   method-method abstract/interface dengan logika spesifik hotel.
 *
 * KONSEP JAVA — Pattern Matching (instanceof):
 *   Di TravelApp.cancelReservation(), kode:
 *     } else if (res instanceof HotelReservation hr) { hr.cancel(); }
 *   mengenali tipe HotelReservation dan membuat variabel 'hr' yang sudah di-cast.
 *
 * TODO [ANGGOTA 2] — Implementasikan semua method yang ditandai TODO.
 *   Pola implementasinya mirip dengan FlightReservation — jadikan referensi!
 *   Perbedaan utama: hotel punya availableRooms (bukan availableSeats).
 */
public final class HotelReservation extends Reservation {

    // Field tambahan spesifik untuk reservasi hotel
    private Hotel hotel;       // objek hotel yang dipesan
    private int guestCount;    // jumlah tamu

    // =================== CONSTRUCTOR ===================

    /**
     * Buat reservasi hotel baru.
     *
     * TODO [ANGGOTA 2]:
     *   Assign setiap parameter ke field yang sesuai:
     *     this.hotel        = hotel;
     *     this.guestCount   = guestCount;
     *     this.customerName    = customerName;    ← field dari Reservation
     *     this.customerContact = customerContact; ← field dari Reservation
     *
     * @param hotel           objek Hotel yang dipesan
     * @param guestCount      jumlah tamu
     * @param customerName    nama pemesan
     * @param customerContact kontak pemesan (telepon/email)
     */
    public HotelReservation(Hotel hotel, int guestCount,
                            String customerName, String customerContact) {
        // TODO [ANGGOTA 2]: assign semua parameter ke fields
        // this.hotel           = hotel;
        // this.guestCount      = guestCount;
        // this.customerName    = customerName;
        // this.customerContact = customerContact;
    }

    // =================== OVERRIDE: book() ===================

    /**
     * Konfirmasi pemesanan hotel.
     *
     * TODO [ANGGOTA 2]:
     *   Langkah 1: Generate nomor konfirmasi:
     *     this.confirmationNumber = ConfirmationNumberGenerator.generate();
     *
     *   Langkah 2: Kurangi kamar tersedia di hotel (1 kamar per pemesanan):
     *     hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
     *
     *   Langkah 3: (Opsional) Tampilkan pesan konfirmasi.
     */
    @Override
    public void book() {
        // TODO [ANGGOTA 2]: implementasikan langkah-langkah di atas
        System.out.println("[TODO] HotelReservation.book() belum diimplementasikan");
    }

    // =================== OVERRIDE: cancel() ===================

    /**
     * Batalkan pemesanan hotel dan kembalikan kamar.
     *
     * TODO [ANGGOTA 2]:
     *   Langkah 1: Kembalikan kamar ke hotel:
     *     hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
     *
     *   Langkah 2: (Opsional) Tampilkan pesan pembatalan.
     */
    @Override
    public void cancel() {
        // TODO [ANGGOTA 2]: implementasikan langkah-langkah di atas
        System.out.println("[TODO] HotelReservation.cancel() belum diimplementasikan");
    }

    // =================== OVERRIDE: display() ===================

    /**
     * Tampilkan detail konfirmasi reservasi hotel ke konsol.
     *
     * TODO [ANGGOTA 2]:
     *   Tampilkan informasi dalam format rapi. Contoh format:
     *
     *   ┌────────────────────────────────────────────┐
     *   │         KONFIRMASI PEMESANAN HOTEL         │
     *   ├────────────────────────────────────────────┤
     *   │ No. Konfirmasi : 831047                    │
     *   │ Nama Tamu      : Siti Rahma                │
     *   │ Kontak         : 08987654321               │
     *   │ Hotel          : Hotel Mulia Jakarta        │
     *   │ Lokasi         : Jakarta                   │
     *   │ Check-in       : 2025-08-01                │
     *   │ Check-out      : 2025-08-03                │
     *   │ Jumlah Tamu    : 2                         │
     *   │ Total Harga    : Rp 1.600.000              │
     *   └────────────────────────────────────────────┘
     *
     *   Hitung malam: hitung selisih hari antara checkIn dan checkOut.
     *   Cara mudah untuk pemula: simpan jumlah malam sebagai parameter atau hitung manual.
     *   Total harga = jumlah malam * hotel.getPricePerNight()
     */
    @Override
    public void display() {
        // TODO [ANGGOTA 2]: implementasikan tampilan di atas dengan System.out.println()
        System.out.println("[TODO] HotelReservation.display() belum diimplementasikan");
        System.out.println("  Konfirmasi: " + confirmationNumber
                + " | Tamu: " + customerName);
    }

    // =================== GETTERS ===================

    /**
     * TODO [ANGGOTA 2]: return nilai field hotel
     */
    public Hotel getHotel() {
        return null; // TODO: return hotel;
    }

    /**
     * TODO [ANGGOTA 2]: return nilai field guestCount
     */
    public int getGuestCount() {
        return 0; // TODO: return guestCount;
    }
}
