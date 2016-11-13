package resources;

import models.Auth;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("auth")
public class AuthRsc {
    @Path("test")
    @GET
    public String test(){
        return "TEST";
    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public String login(Auth auth) {
        if (auth.login.equals("login") && auth.password.equals("password")) {
            JSONObject json = new JSONObject();
            json.put("id", 23423);
            json.put("name", "Vasya");
            return json.toString();
        }
        JSONObject json = new JSONObject();
        json.put("error", "unknows erroo");
        json.put("errorCode", 100);

        return json.toString();
    }
}
