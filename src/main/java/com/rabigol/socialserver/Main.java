package com.rabigol.socialserver;

import com.rabigol.socialserver.model.PushMessage;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.Driver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

/**
 * Created by Artur.Ziaev on 13.11.2016.
 */
public class Main {
    public static Connection dbConnection;
    public static Connection dbConnection2;

//    public static void main(String[] args) {
//        Client client = ClientBuilder.newClient();
//        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
//        headers.add("Authorization", "key=AAAAv2XvNoM:APA91bHo5lgy-1M-7-kHx1PalGbQ9bPN5toejuZGyS1MEezZ4WqnjFbY9iXsCLdfSYs8o886aBvs2rpM9WenwrBeRNeRnHcv7iGWwiDNtjRqIViRJO2wesRsePYcVzszoduKEY9URz9U8UHMwqSNwdJqgaHqqKpTbA");
//        headers.add("Content-Type", "application/json");
//
//        String to = "dl7hqkv5fFU:APA91bEHDFNpXgIZFIuGVXQA4y0N46HpJA2-Lg5IC9oLQbcNdt_9XZIiMb-qJS47dLoKqTYsHlRZ5mN334zKuO6ujl8kMBCmAd4qwsjvxRlObMBqPXDgHbvXKsezn-NPUEPOjNglQf8y";
//        HashMap<String, String> data = new HashMap<String, String>();
//        data.put("title", "Важное сообщение");
//        data.put("message", "C вас списали 400р");
//
//        PushMessage push = new PushMessage(to, data);
//
//        WebTarget target = client.target("https://fcm.googleapis.com/fcm/send");
//        Response response = target.request()
//                .headers(headers)
//                .accept(MediaType.APPLICATION_JSON_TYPE)
//                .post(Entity.json(push));
//
//        System.out.println(response.readEntity(String.class));
//    }

    public static void main(String[] args) throws Exception {


        Driver driver = (Driver) Class.forName("org.postgresql.Driver").newInstance();
        DriverManager.registerDriver(driver);

        //users DB
        dbConnection = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/droid", "postgres", "postgres");

        //Operations DB
        dbConnection2 = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/mydb", "postgres", "postgres");

        Server server = new Server(9090);
////
////        ResourceHandler resourceHandler = new ResourceHandler();
////        resourceHandler.setResourceBase("C:\\Users\\artur.ziaev\\Google Диск\\personal\\projects\\JAVA\\javainnopolislessons\\SocialServer\\public_html");
////        resourceHandler.setDirectoriesListed(true);
////
////        server.setHandler(resourceHandler);
//
////        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
////        context.addServlet(new ServletHolder(new com.rabigol.socialserver.model.SampleServlet()), "/api");
////        server.setHandler(context);
//
//        /* JAX-RS */
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/api/*");
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com/rabigol/socialserver/resources");
        server.setHandler(context);
//
        server.start();
        server.join();
////
////        Statement statement = dbconnection.createStatement();
////        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
////
////        while (resultSet.next()){
////            int id = resultSet.getInt("id");
////            String s = resultSet.getString("name");
////
////            System.out.println("id: " + id + " name " + s);
////        }
////
////        resultSet.close();
////        statement.close();
//
//        dbConnection.close();

    }
}
