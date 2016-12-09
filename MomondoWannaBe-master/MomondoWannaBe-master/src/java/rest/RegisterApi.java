package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.UserAlreadyExistsException;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.PasswordHash;

/**
 *
 * @author user
 */
@Path("register")
public class RegisterApi {

    UserFacade uf = new UserFacade();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response registerUser(String json) throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
        JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
        String userName = jsonObj.get("userName").getAsString();
        if (uf.getUserByUserName(userName) != null) {
            throw new UserAlreadyExistsException();
        }
        entity.User u = new entity.User();
        u.setUserName(userName);
        String password = jsonObj.get("password").getAsString();
        String hash = PasswordHash.createHash(password);
        u.setPasswordHash(hash);
        u.addRole("User");
        uf.addUser(u);
        JsonObject returnObj = new JsonObject();
        returnObj.addProperty("id", u.getId());
        returnObj.addProperty("userName", u.getUserName());
        return Response.status(Response.Status.OK).entity(returnObj.toString()).type(MediaType.APPLICATION_JSON).build();
    }

}
