package org.camunda.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.camunda.stock.DatabaseController;
import org.junit.jupiter.api.Test;

class TestDatabaseConnection {

	@Test
	void createConnection() throws SQLException {
		DatabaseController testDB = new DatabaseController("localhost:3306","stock","camundabpm","disp20", null);
		Connection connection = (Connection) testDB.openConnection();
		assert connection != null;
		if (connection != null) connection.close();
	}
}