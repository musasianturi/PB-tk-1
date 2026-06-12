package interfaces;

/**
 * Interface Bookable mendefinisikan kontrak perilaku untuk semua objek yang dapat
 * dipesan dan dibatalkan dalam sistem pemesanan perjalanan.
 *
 * KONSEP JAVA — Interface:
 *   Interface hanya berisi method signatures (dan constants), tanpa implementasi.
 *   Kelas yang meng-implement interface ini WAJIB menyediakan body untuk setiap method.
 *   FlightReservation dan HotelReservation keduanya akan implements Bookable secara
 *   tidak langsung melalui abstract class Reservation.
 *
 * Dengan interface ini, kita bisa membuat kode polimorfik seperti:
 *   List<Bookable> bookings = new ArrayList<>();
 *   bookings.add(flightReservation);
 *   bookings.add(hotelReservation);
 *   bookings.forEach(b -> b.cancel());  // tanpa perlu tahu tipe spesifiknya
 *
 * FILE INI SUDAH LENGKAP — tidak perlu diubah.
 */
public interface Bookable {

    /**
     * Lakukan proses pemesanan/konfirmasi reservasi.
     *
     * Implementasi di FlightReservation harus:
     *   1. Generate nomor konfirmasi via ConfirmationNumberGenerator.generate()
     *   2. Kurangi availableSeats di Flight sebesar passengerCount
     *
     * Implementasi di HotelReservation harus:
     *   1. Generate nomor konfirmasi via ConfirmationNumberGenerator.generate()
     *   2. Kurangi availableRooms di Hotel sebesar 1
     */
    void book();

    /**
     * Batalkan reservasi yang sudah ada.
     *
     * Implementasi di FlightReservation harus:
     *   1. Kembalikan availableSeats di Flight sebesar passengerCount
     *
     * Implementasi di HotelReservation harus:
     *   1. Kembalikan availableRooms di Hotel sebesar 1
     */
    void cancel();

    /**
     * Kembalikan nomor konfirmasi 6 digit unik untuk reservasi ini.
     * Nomor ini digunakan saat pengguna ingin membatalkan pemesanan.
     *
     * @return nomor konfirmasi, contoh: 472819
     */
    int getConfirmationNumber();
}
