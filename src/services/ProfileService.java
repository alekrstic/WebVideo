package services;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

import model.User;
import model.User.UserType;

public class ProfileService {

	public List<User> getProfiles(){
		Connection conn = ConnectionManager.getConnection();
		
		Statement s = null;
		try {
			String query = "SELECT * FROM videodb.Users";
			
			List<User> users = new ArrayList<>();
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				String firstName = rs.getString(3);
				String lastName = rs.getString(4);
				String email = rs.getString(5);
				String userType = rs.getString(6);
				Boolean blocked = rs.getBoolean(7);
				Date date = rs.getDate(8);
				String description = rs.getString(9);
				
				UserType userTypeEnum =UserType.valueOf(UserType.class, userType);
				User user = new User(username, password, firstName, lastName, email, userTypeEnum, blocked, date, description);
				users.add(user);
			}
			
			s.close();
			return users;

		} catch (Exception e) {
			System.out.println("Catch Exception while geting Users!");
		}
		return null;
	}
}

