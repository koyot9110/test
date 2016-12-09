/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Bancho
 */
@Entity
public class TicketRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String from;
    private String to;
    
    @Temporal(TemporalType.DATE)
    private Date flightDate;
    
    @Temporal(TemporalType.DATE)
    private Date searchDate;

    public TicketRequest() {
    }

    public TicketRequest(String from, Date flightDate, Date searchDate) {
        this.from = from;
        this.flightDate = flightDate;
        this.searchDate = searchDate;
    }

    public TicketRequest(String from, String to, Date flightDate, Date searchDate) {
        this.from = from;
        this.to = to;
        this.flightDate = flightDate;
        this.searchDate = searchDate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date date) {
        this.flightDate = date;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

}
