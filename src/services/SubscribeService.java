package services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class SubscribeService {
	
	public boolean subscribe(String usernameSubscriber, String usernameSubscribed){
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Subscriber (subscriber, subscribed) VALUES (?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, usernameSubscriber);
			pstmt.setString(index++, usernameSubscribed);

			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new subscriber in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;	
	}
	
	public boolean checkSubscribe(String usernameSubscriber, String usernameSubscribed){
		Connection conn = ConnectionManager.getConnection();

		Statement s = null;
		try {
			String query = "SELECT * FROM Subscriber WHERE subscriber = '"+ usernameSubscriber + "' AND subscribed = '" + usernameSubscribed +"'";
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.next())
			{
				return true;
			}
			else {
				return false;
			}

		} catch (SQLException ex) {
			//System.out.println("Error while checking subscriber in database!");
			ex.printStackTrace();
		} finally {
			try {s.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;	
	}

	public List<User> getAllSubscribersByUser(String userName) {
		List<User> subscribers = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();

		Statement s = null;
		try {
			UserService userService = new UserService();
			String query = "SELECT subscriber FROM Subscriber WHERE subscribed = '"+ userName + "'";
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next())
			{
				String usernameSubscriber = rs.getString(1);
				User user = userService.getUser(usernameSubscriber);
				
				subscribers.add(user);
			}
			

		} catch (SQLException ex) {
			System.out.println("Error while checking subscriber in database!");
			ex.printStackTrace();
		} finally {
			try {s.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return subscribers;
	}

}
