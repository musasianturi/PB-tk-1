package model;

/**
 * Kelas Flight merepresentasikan satu jadwal penerbangan yang tersedia di sistem.
 * Setiap objek Flight adalah satu penerbangan dengan rute, tanggal, dan harga tertentu.
 *
 * KONSEP JAVA — Encapsulation:
 *   Semua field dideklarasikan 'private' sehingga tidak bisa diakses langsung dari luar kelas.
 *   Akses hanya melalui public getter dan setter yang kita kendalikan.
 *   Ini melindungi data dari modifikasi tidak terduga.
 *
 * KONSEP JAVA — Constructor:
 *   Constructor digunakan untuk menginisialisasi objek saat dibuat dengan 'new Flight(...)'.
 *   Parameter constructor mewakili data awal yang wajib ada saat penerbangan dibuat.
 *
 * KONSEP JAVA — toString():
 *   Override toString() agar objek bisa ditampilkan langsung dengan System.out.println(flight).
 *
 * TODO [ANGGOTA 1] — Implementasikan bagian-bagian yang ditandai TODO di bawah ini.
 */
public class Flight {

    // =================== FIELDS (private — enkapsulasi) ===================

    private String flightNumber;   // Nomor penerbangan, contoh: "GA-101", "QG-812"
    private String origin;         // Kota keberangkatan, contoh: "Jakarta"
    private String destination;    // Kota tujuan, contoh: "Bali"
    private String date;           // Tanggal penerbangan, format: "YYYY-MM-DD"
    private int availableSeats;    // Jumlah kursi yang masih tersedia
    private double pricePerSeat;   // Harga per kursi dalam Rupiah

    // =================== CONSTRUCTOR ===================

    /**
     * Buat objek Flight baru dengan data penerbangan lengkap.
     *
     * TODO [ANGGOTA 1]:
     *   Isi setiap field 'this.xxx' dengan nilai dari parameter yang sesuai.
     *   Gunakan pola: this.flightNumber = flightNumber;
     *
     * Contoh penggunaan (di TravelApp.initSampleData()):
     *   flights.add(new Flight("GA-101", "Jakarta", "Bali", "2025-08-01", 50, 1_500_000.0));
     *
     * @param flightNumber  nomor penerbangan unik
     * @param origin        kota asal
     * @param destination   kota tujuan
     * @param date          tanggal penerbangan (format YYYY-MM-DD)
     * @param availableSeats jumlah kursi tersedia
     * @param pricePerSeat  harga per kursi dalam Rupiah
     */
    public Flight(String flightNumber, String origin, String destination,
                  String date, int availableSeats, double pricePerSeat) {
        // TODO [ANGGOTA 1]: assign setiap parameter ke field yang sesuai
        this.flightNumber  = flightNumber;
        this.origin        = origin;
        this.destination   = destination;
        this.date          = date;
        this.availableSeats = availableSeats;
        this.pricePerSeat  = pricePerSeat;
    }

    // =================== GETTERS ===================
    // Getter mengembalikan nilai field private ke pemanggil.
    // Pola: public TipeData getNamaField() { return namaField; }

    /**
     * TODO [ANGGOTA 1]: return nilai field flightNumber
     */
    public String getFlightNumber() {
        return flightNumber; // TODO: return flightNumber;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field origin
     */
    public String getOrigin() {
        return origin; // TODO: return origin;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field destination
     */
    public String getDestination() {
        return destination; // TODO: return destination;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field date
     */
    public String getDate() {
        return date; // TODO: return date;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field availableSeats
     */
    public int getAvailableSeats() {
        return availableSeats; // TODO: return availableSeats;
    }

    /**
     * TODO [ANGGOTA 1]: return nilai field pricePerSeat
     */
    public double getPricePerSeat() {
        return pricePerSeat; // TODO: return pricePerSeat;
    }

    // =================== SETTERS ===================
    // Setter digunakan untuk memperbarui nilai field.
    // Pola: public void setNamaField(TipeData namaField) { this.namaField = namaField; }

    /**
     * TODO [ANGGOTA 1]: assign parameter availableSeats ke field this.availableSeats
     * Dipakai oleh FlightReservation.book() dan cancel() untuk update kursi.
     */
    public void setAvailableSeats(int availableSeats) {
        // TODO: this.availableSeats = availableSeats;
        this.availableSeats = availableSeats;
    }

    // =================== TO STRING ===================

    /**
     * Representasi teks dari objek Flight untuk ditampilkan di konsol.
     *
     * TODO [ANGGOTA 1]: Buat format yang informatif.
     *
     * Format yang disarankan:
     *   [GA-101] Jakarta → Bali | Tanggal: 2025-08-01 | Kursi: 50 | Harga: Rp 1.500.000/kursi
     *
     * Tips menggunakan String.format():
     *   String.format("[%s] %s → %s | Tanggal: %s | Kursi: %d | Harga: Rp %,.0f/kursi",
     *                  flightNumber, origin, destination, date, availableSeats, pricePerSeat)
     *   %s  → String
     *   %d  → integer
     *   %,.0f → double dengan pemisah ribuan, tanpa desimal (1500000 → "1.500.000")
     */
    @Override
    public String toString() {
        // TODO [ANGGOTA 1]: implementasikan format di atas menggunakan String.format()
        return String.format(
                "[%s] %s → %s | Tanggal: %s | Kursi: %d | Harga: Rp %,.0f/kursi",
                flightNumber,
                origin,
                destination,
                date,
                availableSeats,
                pricePerSeat
        );
    }
}
