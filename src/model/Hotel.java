package model;

/**
 * Kelas Hotel merepresentasikan satu hotel yang tersedia untuk dipesan.
 * Setiap objek Hotel mewakili satu properti hotel dengan lokasi, harga, dan ketersediaan kamar.
 *
 * KONSEP JAVA — Encapsulation:
 *   Semua field 'private', hanya bisa diakses lewat getter dan setter.
 *   Ini menjaga konsistensi data hotel (misalnya availableRooms tidak bisa dikurangi
 *   sembarangan dari luar kelas — harus lewat setAvailableRooms yang kita kontrol).
 *
 * KONSEP JAVA — Constructor & toString():
 *   Sama seperti Flight, Hotel punya constructor untuk inisialisasi dan toString() untuk display.
 *
 * TODO [ANGGOTA 1] — Implementasikan bagian yang ditandai TODO di bawah.
 *   Pola implementasinya sama persis dengan Flight.java — jadikan referensi!
 */
public class Hotel {

    // =================== FIELDS (private — enkapsulasi) ===================

    private String hotelId;         // ID unik hotel, contoh: "HTL-001"
    private String name;            // Nama hotel, contoh: "Hotel Mulia Jakarta"
    private String location;        // Kota lokasi hotel, contoh: "Jakarta"
    private String checkInDate;     // Tanggal check-in, format: "YYYY-MM-DD"
    private String checkOutDate;    // Tanggal check-out, format: "YYYY-MM-DD"
    private int availableRooms;     // Jumlah kamar yang tersedia
    private double pricePerNight;   // Harga per malam per kamar dalam Rupiah

    // =================== CONSTRUCTOR ===================

    /**
     * Buat objek Hotel baru.
     *
     * TODO [ANGGOTA 1]:
     *   Assign setiap parameter ke field yang sesuai.
     *   Gunakan pola: this.hotelId = hotelId;
     *
     * Contoh penggunaan (di TravelApp.initSampleData()):
     *   hotels.add(new Hotel("HTL-001", "Hotel Mulia Jakarta", "Jakarta",
     *                        "2025-08-01", "2025-08-03", 10, 800_000.0));
     *
     * @param hotelId        ID unik hotel
     * @param name           nama hotel
     * @param location       kota lokasi hotel
     * @param checkInDate    tanggal check-in (YYYY-MM-DD)
     * @param checkOutDate   tanggal check-out (YYYY-MM-DD)
     * @param availableRooms jumlah kamar tersedia
     * @param pricePerNight  harga per malam dalam Rupiah
     */
    public Hotel(String hotelId, String name, String location,
                 String checkInDate, String checkOutDate,
                 int availableRooms, double pricePerNight) {
        this.hotelId        = hotelId;
        this.name           = name;
        this.location       = location;
        this.checkInDate    = checkInDate;
        this.checkOutDate   = checkOutDate;
        this.availableRooms = availableRooms;
        this.pricePerNight  = pricePerNight;
    }

    // =================== GETTERS ===================

    /**
     * TODO [ANGGOTA 1]: return nilai field hotelId
     */
    public String getHotelId() {
        return hotelId;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field name
     */
    public String getName() {
        return name;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field location
     */
    public String getLocation() {
        return location;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field checkInDate
     */
    public String getCheckInDate() {
        return checkInDate;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field checkOutDate
     */
    public String getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field availableRooms
     */
    public int getAvailableRooms() {
        return availableRooms;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field pricePerNight
     */
    public double getPricePerNight() {
        return pricePerNight;
    }

    // =================== SETTERS ===================

    /**
     * TODO [ANGGOTA 1]: assign parameter availableRooms ke this.availableRooms
     * Dipakai oleh HotelReservation.book() dan cancel() untuk update kamar.
     */
    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    // =================== TO STRING ===================

    /**
     * TODO [ANGGOTA 1]: Buat representasi teks hotel yang informatif.
     *
     * Format yang disarankan:
     *   [HTL-001] Hotel Mulia Jakarta | Lokasi: Jakarta | Check-in: 2025-08-01
     *   Check-out: 2025-08-03 | Kamar: 10 | Harga: Rp 800.000/malam
     *
     * Gunakan String.format() seperti di Flight.toString().
     */
    @Override
    public String toString() {
        return String.format(
                "[%s] %s | Lokasi: %s | Check-in: %s | Check-out: %s | Kamar: %d | Harga: Rp %,.0f/malam",
                hotelId, name, location, checkInDate, checkOutDate, availableRooms, pricePerNight
        );
    }
}
