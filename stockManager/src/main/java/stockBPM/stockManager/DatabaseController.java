package stockBPM.stockManager;

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

	public void openConnection() {
		try {

			//Create a connection to the database

			String url = "jdbc:mysql://" + serverName +  "/" + schema;
			System.out.println(url);
			connection = DriverManager.getConnection(url, username, password);


			System.out.println("Successfully Connected to the database!");


		} catch (SQLException e) {

			System.out.println("Could not connect to the database " + e.getMessage());
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
		
	public String restock (String itemName, Integer quantity) throws SQLException {
		if (quantity < 1) {
			return "Order quantity error: Quantity " + quantity + "must be greater than 0";
		}
		PreparedStatement updt = connection.prepareStatement("update fish set quantity = quantity + ? where name = ?");
		updt.setInt(1, quantity);
		updt.setString(2, itemName);
		updt.executeUpdate();
		return quantity + " new stock ordered for " + itemName;
	}
		
	public Object newOrder (orderItem order) throws SQLException {
		if (order.getOrderQuantity() < 1) {
			return "Order quantity error: Quantity must be at least 1";
		}
		PreparedStatement query = connection.prepareStatement("select quantity from fish where name = ?");
		query.setString(1, order.getName());
		ResultSet rs = query.executeQuery();
		rs.next();
		int currentQuantity = rs.getInt(1);
		if (currentQuantity < 1) {
			return order.getName() + " is out of stock.";
		}
		if (order.getOrderQuantity() > currentQuantity) {
			return "Order size error: Stock order of " + order.getOrderQuantity() + " exceeds stock level for " + order.getName() + " (" + currentQuantity +")";
		}
		PreparedStatement updt = connection.prepareStatement("update fish set quantity = quantity - ? where name = ?");
		updt.setInt(1, order.getOrderQuantity());
		updt.setString(2, order.getName());
		updt.executeUpdate();
		return "New Order for " + order.getOrderQuantity() + " x " + order.getName();
		
	}
	public int getStockQuantitiy(stockItem item) {
		return item.getQuantity();
	}
	
}
