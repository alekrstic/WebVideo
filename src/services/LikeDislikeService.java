package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.LikeDislike;

public class LikeDislikeService {
	
	
	public boolean addLikeDislike(LikeDislike likeDislike){
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			
			String queryCheck;
			if(likeDislike.getVideo() != null) {
				queryCheck = "SELECT * from LikeDislike WHERE username='"
						+ likeDislike.getUser().getUsername() + "'AND videoID='" + likeDislike.getVideo().getId() + "'"; 
			}
			else {
				queryCheck = "SELECT * from LikeDislike WHERE username='"
						+ likeDislike.getUser().getUsername() + "'AND commentID='" + likeDislike.getComment().getId() + "'"; 
			}
			
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(queryCheck);
			
			
			if (rs.next()) {								
				if(likeDislike.getVideo() != null){					
					String query = "UPDATE LikeDislike SET isLike=? WHERE username=? AND videoID=?";
					pstmt = conn.prepareStatement(query);
					int index = 1;
					pstmt.setBoolean(index++, likeDislike.isLike());
					pstmt.setString(index++, likeDislike.getUser().getUsername());
					pstmt.setLong(index++, likeDislike.getVideo().getId());
				}
				
				else {
					String query = "UPDATE LikeDislike SET isLike=? WHERE username=? AND commentID=?";
					pstmt = conn.prepareStatement(query);
					int index = 1;
					pstmt.setBoolean(index++, likeDislike.isLike());
					pstmt.setString(index++, likeDislike.getUser().getUsername());
					pstmt.setLong(index++, likeDislike.getComment().getId());
				}				
			}
			else {
			
				String query = "INSERT INTO LikeDislike (videoID, commentID, username, isLike, date_likeDislike) VALUES (?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(query);
				int index = 1;
				if( likeDislike.getVideo() != null) {
					pstmt.setLong(index, likeDislike.getVideo().getId());
				}
				else {
					pstmt.setNull(index, java.sql.Types.NULL);
				}
				index++;
				if (likeDislike.getComment() != null) {
					pstmt.setLong(index, likeDislike.getComment().getId());
				}
				else {
					pstmt.setNull(index, java.sql.Types.NULL);
				}
			index++;
			pstmt.setString(index++, likeDislike.getUser().getUsername());
			pstmt.setBoolean(index++, likeDislike.isLike());
			pstmt.setDate(index++, likeDislike.getDate());			
			}
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new Like/Dislike in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;	
	}
	
	public List<String> getAllLikesByVideo (Long videoID, boolean isLike) {
		Connection conn = ConnectionManager.getConnection();
		Statement s = null;
		
		List<String> usernames = new ArrayList<>();
		try {
			String query = "SELECT username FROM LikeDislike WHERE videoID='" + videoID + "' AND isLike=" + isLike;
			
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while (rs.next()) {
				String username = rs.getString(1);	
				usernames.add(username);
			}
			s.close();
			return usernames;			
			
			
		} catch (Exception e) {
			
		}										
		return null;
	}
	
	public List<String> getAllLikesByComment (Long commentID, boolean isLike) {
		Connection conn = ConnectionManager.getConnection();
		Statement s = null;
		
		List<String> usernames = new ArrayList<>();
		try {
			String query = "SELECT username FROM LikeDislike WHERE commentID='" + commentID + "' AND isLike=" + isLike;
			
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while (rs.next()) {
				String username = rs.getString(1);	
				usernames.add(username);
			}
			s.close();
			return usernames;			
			
			
		} catch (Exception e) {
			
		}										
		return null;
	}


}
