package model;

import util.ConfirmationNumberGenerator;


public final class FlightReservation extends Reservation {

    private Flight flight;
    private int passengerCount;

    public FlightReservation(Flight flight, int passengerCount,
                             String customerName, String customerContact) {
        this.flight          = flight;
        this.passengerCount  = passengerCount;
        this.customerName    = customerName;
        this.customerContact = customerContact;
    }

    @Override
    public void book() {
        this.confirmationNumber = ConfirmationNumberGenerator.generate();
        flight.setAvailableSeats(flight.getAvailableSeats() - passengerCount);
        System.out.println("Pemesanan berhasil! No. Konfirmasi: " + confirmationNumber);
    }

    @Override
    public void cancel() {
        flight.setAvailableSeats(flight.getAvailableSeats() + passengerCount);
        System.out.println("Pemesanan penerbangan " + flight.getFlightNumber() + " dibatalkan.");
    }

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

    public Flight getFlight()       { return flight; }
    public int getPassengerCount()  { return passengerCount; }
}
