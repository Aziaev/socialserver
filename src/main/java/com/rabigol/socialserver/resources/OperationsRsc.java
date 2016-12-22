package com.rabigol.socialserver.resources;

import com.rabigol.socialserver.Main;
import com.rabigol.socialserver.model.GetBalance;
import com.rabigol.socialserver.model.Operation;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("operations")

public class OperationsRsc {
    @POST
    @Path("/{userId}")
    public String getOperations(@PathParam("userId") int userId) {
        Connection connection = Main.dbConnection2;
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM operations WHERE owner_id = ? ORDER BY timestamp DESC");
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                jsonObject = new JSONObject();
                jsonObject.put("id", resultSet.getInt("id"));
                jsonObject.put("account", resultSet.getString("account"));
                jsonObject.put("currency", resultSet.getString("currency"));
                jsonObject.put("description", resultSet.getString("description"));
                jsonObject.put("operation_category", resultSet.getString("operation_category"));
                jsonObject.put("operation_type", resultSet.getString("operation_type"));
                jsonObject.put("timestamp", resultSet.getInt("timestamp"));
                jsonObject.put("value", resultSet.getInt("value"));
                jsonArray.put(jsonObject);
            }
            System.out.println(jsonArray);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }

    @Path("/balance/total")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getBalance(GetBalance getBalance) {
        Connection connection = Main.dbConnection2;
        JSONObject jsonObject = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT SUM(value) FROM operations WHERE owner_id = ? AND currency = ?");
            preparedStatement.setInt(1, getBalance.userId);
            preparedStatement.setString(2, getBalance.currency);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            System.out.println("Loading balance");
            if (resultSet.next()) {
                jsonObject = new JSONObject();
                jsonObject.put("sum", resultSet.getLong("sum"));
                System.out.println(resultSet.getLong("sum"));
                resultSet.close();
                preparedStatement.close();
                return jsonObject.toString();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Path("edit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editOperation(Operation operation) {
        Connection connection = Main.dbConnection2;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("edited", "false");

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE operations SET owner_Id = ?, timestamp = ?, operation_Type = ?, operation_Category = ?, account = ?, value = ?, currency = ?, description = ? WHERE id = ?");
            preparedStatement.setLong(1, operation.ownerId);
            preparedStatement.setLong(2, operation.timestamp);
            preparedStatement.setString(3, operation.operation_Type);
            preparedStatement.setString(4, operation.operation_Category);
            preparedStatement.setString(5, operation.account);
            preparedStatement.setLong(6, operation.value);
            preparedStatement.setString(7, operation.currency);
            preparedStatement.setString(8, operation.description);
            preparedStatement.setLong(9, operation.operationId);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                jsonObject = new JSONObject();
                jsonObject.put("edited", "true");
                resultSet.close();
                preparedStatement.close();
                return jsonObject.toString();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            jsonObject = new JSONObject();
            jsonObject.put("error", "editing error" + e);
        }
        return jsonObject.toString();
    }

    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteOperation(Operation operation) {
        Connection connection = Main.dbConnection2;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("edited", "false");

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM operations WHERE id = ?");
            preparedStatement.setLong(1, operation.operationId);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("resultSet = " + resultSet);
            if (resultSet.next()) {
                jsonObject = new JSONObject();
                jsonObject.put("" + operation.ownerId + "deleted", "true");
                jsonObject.put("errorCode", 100);
                resultSet.close();
                preparedStatement.close();
                return jsonObject.toString();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            jsonObject = new JSONObject();
            jsonObject.put("error", "" + operation.ownerId + "not deleted");
            jsonObject.put("errorCode", 100);
        }
        return jsonObject.toString();
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newOperation(Operation operation) {
        Connection connection = Main.dbConnection2;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Operation added", "false");

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO operations (owner_Id, timestamp, operation_Type, operation_Category, account, value, currency, description) VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setLong(1, operation.ownerId);
            preparedStatement.setLong(2, operation.timestamp);
            preparedStatement.setString(3, operation.operation_Type);
            preparedStatement.setString(4, operation.operation_Category);
            preparedStatement.setString(5, operation.account);
            preparedStatement.setLong(6, operation.value);
            preparedStatement.setString(7, operation.currency);
            preparedStatement.setString(8, operation.description);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            System.out.println(resultSet.getString("updateCount"));
            if (resultSet.next()) {
                jsonObject = new JSONObject();
                jsonObject.put("Operation added", "true");
                resultSet.close();
                preparedStatement.close();
                return jsonObject.toString();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            jsonObject = new JSONObject();
            jsonObject.put("error", "editing error" + e);
        }
        return jsonObject.toString();
    }
}
