package model;

import interfaces.Bookable;


public sealed abstract class Reservation implements Bookable
        permits FlightReservation, HotelReservation {

    protected int confirmationNumber;
    protected String customerName;
    protected String customerContact;

    public abstract void display();

    @Override
    public int getConfirmationNumber()  { return confirmationNumber; }

    public String getCustomerName()     { return customerName; }
    public String getCustomerContact()  { return customerContact; }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }
}
