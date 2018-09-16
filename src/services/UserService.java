package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dto.UserLoginDTO;
import model.User;
import model.User.UserType;

public class UserService {
	
	public User getUser(String username){
		Connection conn = ConnectionManager.getConnection();
		
		Statement s = null;
		try {
			String query = "SELECT * FROM Users WHERE username = '"+username +"'";
			
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			User user=null;
			if (rs.next()) {
				String userName = rs.getString(1);
				String password = rs.getString(2);
				String firstName = rs.getString(3);
				String lastName = rs.getString(4);
				String email = rs.getString(5);
				String userType = rs.getString(6);
				Boolean blocked = rs.getBoolean(7);
				Date dateUser = rs.getDate(8);
				String description = rs.getString(9);
				
				UserType userTypeEnum =UserType.valueOf(UserType.class, userType);
				user = new User(userName, password, firstName, lastName, email, userTypeEnum, blocked, dateUser, description);

				
			}
			s.close();
			return user;
			
		} catch (Exception e) {
			
		}
		return null;
	}
	
	
	
	public boolean addUser(User user){
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Users (username, password,firstName,lastName, email, userType, blocked, date_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getUserType().toString());
			pstmt.setBoolean(index++, user.isBlocked());
			pstmt.setDate(index++, user.getDate());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new user in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;	
	}
	
	private UserLoginDTO getUserForLogin(String userName) {
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
	
			String query = "SELECT username ,password, userType FROM Users WHERE userName = ?"; 
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				String userType = rset.getString(index++);

				return new UserLoginDTO(username, password, UserType.valueOf(userType));
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}

		return null;
	}
	

	public boolean checkUser(UserLoginDTO logUser) {
		UserLoginDTO user =  this.getUserForLogin(logUser.getUsername());
		logUser.setUserType(user.getUserType());
		if(user == null || !user.getPassword().equals(logUser.getPassword())) {
			return false;
		}
		return true;
	}



	public boolean updateUser(User updateUser) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Users SET firstName=?, lastName=?, description_user=?, password=? WHERE username=?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, updateUser.getFirstName());
			pstmt.setString(index++, updateUser.getLastName());
			pstmt.setString(index++, updateUser.getDescription());
			pstmt.setString(index++, updateUser.getPassword());
			pstmt.setString(index++, updateUser.getUsername());
			
		
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while updating user in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
		
		
	}



	public boolean blockUser(String username, boolean blocked) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Users SET blocked=? WHERE username=?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setBoolean(index++, blocked);
			pstmt.setString(index++, username);
				
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while blocking USER in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}



	public boolean deleteUser(String username) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM Users WHERE username=?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, username);
			
			return pstmt.executeUpdate() == 1;

		} catch (SQLException ex) {
			System.out.println("Error while deleting USER from database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}





	

}