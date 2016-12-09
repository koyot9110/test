/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Mato
 */
@Entity
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightID, reserveeName, reservePhone, reserveEmail;
    private int numberOfSeats;
    @ElementCollection()
    private List<String> passengers;
    @ManyToOne
    private Airline airline;
    @ManyToOne
    private User reservee;

    public Long getId() {
        return id;
    }

    public Booking() {
    }
    

    public Booking(String flightID, String ReserveeName, String ReservePhone, String ReserveEmail, int numberOfSeats, ArrayList<String> passengers) {
        this.flightID = flightID;
        this.reserveeName = ReserveeName;
        this.reservePhone = ReservePhone;
        this.reserveEmail = ReserveEmail;
        this.numberOfSeats = numberOfSeats;
        this.passengers = passengers;
    }

    public User getReservee() {
        return reservee;
    }

    public void setReservee(User reservee) {
        this.reservee = reservee;
    }


    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    
    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getReserveeName() {
        return reserveeName;
    }

    public void setReserveeName(String ReserveeName) {
        this.reserveeName = ReserveeName;
    }

    public String getReservePhone() {
        return reservePhone;
    }

    public void setReservePhone(String ReservePhone) {
        this.reservePhone = ReservePhone;
    }

    public String getReserveEmail() {
        return reserveEmail;
    }

    public void setReserveEmail(String ReserveEmail) {
        this.reserveEmail = ReserveEmail;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<String> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Booking{" + "flightID=" + flightID + ", ReserveeName=" + reserveeName + ", ReservePhone=" + reservePhone + ", ReserveEmail=" + reserveEmail + ", numberOfSeats=" + numberOfSeats + ", passengers=" + passengers + '}';
    }

}