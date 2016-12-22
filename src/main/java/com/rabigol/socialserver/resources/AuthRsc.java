package com.rabigol.socialserver.resources;

import com.rabigol.socialserver.Auth;
import com.rabigol.socialserver.Main;
import com.rabigol.socialserver.model.Registration;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("auth")
public class AuthRsc {

    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(Registration registration) {
        Connection connection = Main.dbConnection;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users(email, name, password, phone) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, registration.email);
            preparedStatement.setString(2, registration.name);
            preparedStatement.setString(3, registration.password);
            preparedStatement.setString(4, registration.phone);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", userId);
                jsonObject.put("name", registration.name);
                jsonObject.put("phone", registration.phone);
                jsonObject.put("email", registration.email);
                jsonObject.put("status", "");

                resultSet.close();
                preparedStatement.close();
                return jsonObject.toString();
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("error", "unknows error");
        json.put("errorCode", 100);

        return json.toString();
    }

    @Path("test")
    @GET
    public String test() {
        return "TEST";
    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public String login(Auth auth) {
        Connection connection = Main.dbConnection;
        JSONObject jsonObject = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            preparedStatement.setString(1, auth.login);
            preparedStatement.setString(2, auth.password);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                jsonObject = new JSONObject();
                jsonObject.put("id", resultSet.getInt("id"));
                jsonObject.put("email", resultSet.getString("email"));

                resultSet.close();
                preparedStatement.close();
                return jsonObject.toString();
            } else {
                jsonObject = new JSONObject();
                jsonObject.put("error", "unknown error");
                jsonObject.put("errorCode", 100);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
