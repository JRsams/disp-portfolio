package stockBPM.stockManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.javatuples.Quartet;

public class Database {
	private String serverName;

	private String schema; 

	private String username;

	private String password;

	private Connection connection;

	public Database() {}

	public Database(String serverName,String schema,
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
	
	public ArrayList<Object> query (String statement) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(statement);
		ResultSet rs = stmt.executeQuery();
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
	public String restockItem(String itemName) {
		
		return null;
	}

}
