package iteration1.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
	Connection conn;
	String url;
	
	public SQLiteConnection() {									// PLEASE NOTE: TWO ""SQLiteConnection"" Methods!!!
		this.conn = null;										// One takes no URL and defaults to the database we use to store username passwords etc
		this.url = "jdbc:sqlite:src/harvardConnection.sl3";		// Other one takes a URL as input incase we use two different Databases
		
		try {
			conn = DriverManager.getConnection(getUrl());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public SQLiteConnection(String url) {							// PLEASE NOTE: TWO ""SQLiteConnection"" Methods!!!
		this.conn = null;											// One takes no URL and defaults to the database we use to store username passwords etc
		this.url = url;												// Other one takes a URL as input incase we use two different Databases
		
		try {
			conn = DriverManager.getConnection(getUrl());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
