package rest;

import booking_engine.BookEngine;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Airline;
import entity.Booking;
import exceptions.BookingException;
import facades.AirlineFacade;
import facades.BookingFacade;
import facades.UserFacade;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mato
 */
@Path("booking")
public class BookingApi {

    BookingFacade bf = new BookingFacade();
    AirlineFacade af = new AirlineFacade();
    UserFacade uf = new UserFacade();
    BookEngine be = new BookEngine();

    @GET
    @Path("getBookings/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsForUser(@PathParam("username") String username) {
        //JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        entity.User reservee = uf.getUserByUserName(username);
        List<Booking> books = bf.getBookingByUser(reservee);
        Gson g = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (entity.Booking b : books) {
            jsonArray.add(new JsonParser().parse(g.toJson(b)));

        }

        return Response.status(Response.Status.OK).entity(jsonArray.toString()).type(MediaType.APPLICATION_JSON).build();

//            return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated USERS\"}";
    }

    @GET
    @Path("getBookings")
    @RolesAllowed("Admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookings() {
          //JsonObject obj = new JsonParser().parse(json).getAsJsonObject();

        List<Booking> books = bf.getBookings();
        Gson g = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (entity.Booking b : books) {
            jsonArray.add(new JsonParser().parse(g.toJson(b)));

        }

        return Response.status(Response.Status.OK).entity(jsonArray.toString()).type(MediaType.APPLICATION_JSON).build();

//            return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated USERS\"}";
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response registerBooking(String json) throws BookingException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        entity.User reservee = uf.getUserByUserName(obj.get("username").getAsString());
        Airline airline = af.getAirlineByName(obj.get("airlineName").getAsString());
        Gson g = new Gson();
        entity.Booking b = g.fromJson(json, Booking.class);
        b.setReservee(reservee);
        b.setAirline(airline);

        JsonObject response = be.book(b);
        if (response.get("code").getAsInt() == 200) {
            bf.addBooking(b);
        } else {
            throw new BookingException(response.toString());
        }

        return Response.status(Response.Status.OK).entity(response.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
