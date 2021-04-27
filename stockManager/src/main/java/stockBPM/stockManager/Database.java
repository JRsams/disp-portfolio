package stockBPM.stockManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public String checkStock(String item) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT quantity FROM `fish` WHERE `name` = ?");
		stmt.setString(1, item);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			String value = rs.getString(1);
			return value;
		}
		return null;
		
	}

}
