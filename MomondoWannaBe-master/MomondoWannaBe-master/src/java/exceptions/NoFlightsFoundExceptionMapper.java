/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Bancho
 */
@Provider
public class NoFlightsFoundExceptionMapper implements ExceptionMapper<NoFlightsFoundException> {
    
    @Context
    ServletContext context;
    
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(NoFlightsFoundException e) {
        JsonObject eObj = new JsonObject();
        eObj.addProperty("httpError", 404);
        eObj.addProperty("errorCode", 1);
        eObj.addProperty("msg", "No flights found for the specified search criteria");
        return Response.status(Response.Status.NOT_FOUND)
                .entity(gson.toJson(eObj))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
