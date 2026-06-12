package exceptions;

/**
 * Custom exception yang dilempar ketika reservasi dengan nomor konfirmasi tertentu
 * tidak ditemukan dalam daftar reservasi aktif.
 *
 * KONSEP JAVA — Custom Exception:
 *   Dengan membuat Exception sendiri (extends Exception), kita bisa:
 *   - Memberi nama yang bermakna pada kondisi error spesifik domain bisnis
 *   - Menyertakan pesan error yang informatif bagi pengguna
 *   - Memisahkan handling error per jenisnya di blok catch
 *
 * Cara pakai di TravelApp.cancelReservation():
 *   throw new ReservationNotFoundException(confirmationNumber);
 *
 * Cara tangkap di caller (misal di Main atau TravelApp):
 *   try {
 *       app.cancelReservation(number);
 *   } catch (ReservationNotFoundException e) {
 *       System.out.println(e.getMessage());
 *   }
 *
 * FILE INI SUDAH LENGKAP — tidak perlu diubah.
 */
public class ReservationNotFoundException extends Exception {

    private final int confirmationNumber;

    /**
     * @param confirmationNumber nomor konfirmasi yang tidak ditemukan
     */
    public ReservationNotFoundException(int confirmationNumber) {
        super("Reservasi dengan nomor konfirmasi [" + confirmationNumber + "] tidak ditemukan. "
                + "Pastikan nomor konfirmasi yang dimasukkan sudah benar.");
        this.confirmationNumber = confirmationNumber;
    }

    /**
     * @return nomor konfirmasi yang menyebabkan exception ini dilempar
     */
    public int getConfirmationNumber() {
        return confirmationNumber;
    }
}
