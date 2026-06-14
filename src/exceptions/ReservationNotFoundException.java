package exceptions;

/**
 * Throw ketika reservasi dengan nomor konfirmasi tertentu tidak ditemukan
 * dalam daftar reservasi aktif.
 */
public class ReservationNotFoundException extends Exception {

    private final int confirmationNumber;


    public ReservationNotFoundException(int confirmationNumber) {
        super("Reservasi dengan nomor konfirmasi [" + confirmationNumber + "] tidak ditemukan. "
                + "Pastikan nomor konfirmasi yang dimasukkan sudah benar.");
        this.confirmationNumber = confirmationNumber;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }
}
