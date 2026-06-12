package model;

import interfaces.Bookable;

/**
 * Abstract class Reservation adalah superclass untuk semua jenis reservasi.
 *
 * KONSEP JAVA — sealed abstract class:
 *   'sealed' → membatasi kelas mana yang boleh meng-extend class ini.
 *              Hanya FlightReservation dan HotelReservation yang diizinkan.
 *              Compiler akan error jika ada class lain mencoba extends Reservation.
 *   'abstract' → class ini tidak bisa diinstansiasi langsung dengan 'new Reservation()'.
 *               Harus melalui subclass-nya.
 *   'permits' → mendaftarkan secara eksplisit subclass yang diizinkan.
 *
 * KONSEP JAVA — abstract method:
 *   Method 'display()' tidak punya body di sini. Setiap subclass WAJIB mengimplementasikannya.
 *   Ini memastikan semua jenis reservasi bisa ditampilkan, dengan cara yang berbeda-beda.
 *
 * KONSEP JAVA — implements Bookable:
 *   Reservation menyatakan bahwa ia mengimplementasikan Bookable, tapi method book()
 *   dan cancel() dibiarkan abstract (tidak diimplementasikan di sini). Subclass yang
 *   konkret (FlightReservation, HotelReservation) yang wajib mengimplementasikannya.
 *
 * FILE INI SUDAH LENGKAP — tidak perlu diubah oleh tim.
 * (Kecuali jika ingin menambah field bersama yang relevan untuk semua reservasi)
 */
public sealed abstract class Reservation implements Bookable
        permits FlightReservation, HotelReservation {

    // Field bersama yang dimiliki oleh SEMUA jenis reservasi
    protected int confirmationNumber;  // nomor konfirmasi 6 digit, diisi saat book() dipanggil
    protected String customerName;     // nama pemesan
    protected String customerContact;  // kontak pemesan (telepon/email)

    /**
     * Tampilkan detail lengkap reservasi ini ke konsol.
     *
     * TODO [ANGGOTA 2] — implementasikan di FlightReservation dan HotelReservation.
     *   FlightReservation.display() → tampilkan: nomor konfirmasi, nama, penerbangan, rute,
     *                                  tanggal, jumlah tiket, total harga.
     *   HotelReservation.display()  → tampilkan: nomor konfirmasi, nama, hotel, lokasi,
     *                                  check-in, check-out, jumlah tamu, total harga.
     */
    public abstract void display();

    // =================== GETTERS & SETTERS ===================

    @Override
    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }
}
