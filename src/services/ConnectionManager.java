package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String DATABASE = "jdbc:mysql://localhost/videodb";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";	

	private static Connection connection;
	
	public static void open(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DATABASE, USER_NAME, PASSWORD);			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static Connection getConnection(){
		return connection;
	}
	public static void close(){
		try{
			connection.close();
		}catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
}



