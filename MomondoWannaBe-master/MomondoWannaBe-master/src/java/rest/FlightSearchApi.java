/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.TicketRequest;
import exceptions.NoFlightsFoundException;
import facades.TicketRequestFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import search_engine.SearchEngine;

/**
 *
 * @author Bancho
 */
@Path("flightinfo")
public class FlightSearchApi {
    
    private SearchEngine searchEngine;
    private TicketRequestFacade trf;

    public FlightSearchApi(){
        searchEngine = new SearchEngine();
        trf = new TicketRequestFacade();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{date}/{numTickets}")
    public Response getFlightResults(@PathParam("from") String from, @PathParam("date") String date, @PathParam("numTickets") String numTickets) throws NoFlightsFoundException{
        
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            trf.addTicketRequest(new TicketRequest(from, sdfISO.parse(date), new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(FlightSearchApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<String> flightsByAirlines = searchEngine.findFlights(from, date, numTickets);
        
        JsonArray jsonFlights = new JsonArray();
        for (String flight : flightsByAirlines) {
            JsonObject jsonFlight = new JsonParser().parse(flight).getAsJsonObject();
            if (jsonFlight.get("airline") != null && jsonFlight.get("flights") != null) {
                jsonFlights.add(jsonFlight);
            }
        }
        
        if(jsonFlights.size() == 0){
            throw new NoFlightsFoundException();
        }
        
        return Response.status(Response.Status.OK).entity(jsonFlights.toString()).type(MediaType.APPLICATION_JSON).build();
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{from}/{to}/{date}/{numTickets}")
    public Response getFlightResults2(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("numTickets") String numTickets) throws NoFlightsFoundException{
        
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            trf.addTicketRequest(new TicketRequest(from, to, sdfISO.parse(date), new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(FlightSearchApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<String> flightsByAirlines = searchEngine.findFlights(from, to, date, numTickets);

        JsonArray jsonFlights = new JsonArray();
        for (String flight : flightsByAirlines) {
            JsonObject jsonFlight = new JsonParser().parse(flight).getAsJsonObject();
            if (jsonFlight.get("airline") != null && jsonFlight.get("flights") != null) {
                jsonFlights.add(jsonFlight);
            }
        }
        
        if(jsonFlights.size() == 0){
            throw new NoFlightsFoundException();
        }
        
        return Response.status(Response.Status.OK).entity(jsonFlights.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
