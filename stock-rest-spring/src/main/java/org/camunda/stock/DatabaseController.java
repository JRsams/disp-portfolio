package org.camunda.stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseController {
	private String serverName;

	private String schema; 

	private String username;

	private String password;

	private Connection connection;

	public DatabaseController() {}

	public DatabaseController(String serverName,String schema,
			String username, String password, Connection connection) {
		super();
		this.serverName = serverName;
		this.schema = schema;
		this.username = username;
		this.password = password;
		this.connection = connection;

	}

	public Connection openConnection() {
		try {

			//Create a connection to the database

			String url = "jdbc:mysql://" + serverName +  "/" + schema;
			
			connection = DriverManager.getConnection(url, username, password);
			return connection;


		} catch (SQLException e) {

			System.out.println("Could not connect to the database " + e.getMessage());
			return null;
		}
		
	}

	public void closeConnection() throws SQLException {
		connection.close();
	}
	
	public ArrayList<Object> read (String statement) throws SQLException {
		PreparedStatement query = connection.prepareStatement(statement);
		ResultSet rs = query.executeQuery();
		ArrayList<Object> resultList = new ArrayList<Object>();
		while (rs.next()) {
				try {
		        Object name = rs.getObject("name"); 
		        resultList.add(name);
				}
				catch (SQLException e) {} 
				try {
		        Object price = rs.getObject("price"); 
		        resultList.add("£"+ price);
				}
				catch (SQLException e) {} 
				try {
		        Object quantity = rs.getObject("quantity"); 
		        resultList.add("Stock count " + quantity);
				}
				catch (SQLException e) {} 
		        
		    }
		return resultList;
	}
		
	public Response restock (Order order) throws SQLException {
		Response response = new Response();
		if (order.getQuantity() < 1) {
			response.setMessage("Order quantity error: Quantity " + order.getQuantity() + " must be greater than 0");
			response.setResult("error");
			return response;
		}
		PreparedStatement updt = connection.prepareStatement("update fish set quantity = quantity + ? where name = ?");
		updt.setInt(1, order.getQuantity());
		updt.setString(2, order.getName());
		updt.executeUpdate();
		response.setMessage(order.getQuantity() + " new stock ordered for " + order.getName());
		response.setResult("success");
		return response;
	}
		
	public Response newOrder (Order order) throws SQLException {
		Response response = new Response();
		if (order.getQuantity() < 1) {
			response.setMessage("Order quantity error: Quantity must be at least 1");
			response.setResult("error");
			return response;
		}
		PreparedStatement query = connection.prepareStatement("select quantity from fish where name = ?");
		query.setString(1, order.getName());
		ResultSet rs = query.executeQuery();
		if (!rs.isBeforeFirst()) {
			response.setMessage("Order name error: No item found with name " + order.getName());
			response.setResult("error");
			return response;
		}
		rs.next();
		int currentQuantity = rs.getInt(1);
		if (currentQuantity < 1) {
			response.setMessage(order.getName() + " is out of stock.");
			response.setResult("failure");
			return response;
		}
		if (order.getQuantity() > currentQuantity) {
			response.setMessage("Order size error: Stock order of " + order.getQuantity() + " exceeds stock level for " + order.getName() + " (" + currentQuantity +")");
			response.setResult("error");
			return response;
		}
		PreparedStatement updt = connection.prepareStatement("update fish set quantity = quantity - ? where name = ?");
		updt.setInt(1, order.getQuantity());
		updt.setString(2, order.getName());
		updt.executeUpdate();
		response.setMessage("New Order for " + order.getQuantity() + " x " + order.getName());
		response.setResult("succsess");
		return response;
		
	}
	public int getStockQuantitiy(stockItem item) {
		return item.getQuantity();
	}
	
}
