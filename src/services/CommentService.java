package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.CommentDTO;
import model.Comment;

public class CommentService {
	
	private LikeDislikeService likeDislikeService;
	
	public CommentService(){
		likeDislikeService = new LikeDislikeService();	
	}
	
	public List<CommentDTO> getCommentsByVideo(Long id) {
		Connection conn = ConnectionManager.getConnection();
		Statement s = null;
		
		List<CommentDTO> comments = new ArrayList<>();
		
		try {
			String query = "SELECT * FROM Comments WHERE videoID='" + id + "'";
			
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while (rs.next()) {
				Long commentID = rs.getLong(1);
				String username = rs.getString(2);
				Long videoID = rs.getLong(3);
				String content = rs.getString(4);
				//Date date_comment = rs.getDate(5);
				
				List<String> likes = likeDislikeService.getAllLikesByComment(commentID, true);
				List<String> dislikes = likeDislikeService.getAllLikesByComment(commentID, false);
				
				CommentDTO comment = new CommentDTO(commentID, username, videoID, content, likes, dislikes);
				comments.add(comment);
	
			}
			s.close();
			return comments;			
			
			
		} catch (Exception e) {
			
		}						
		return null;
	}
	
	public boolean addComment(Comment comment) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Comments (username, videoID, content, date_comment) VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			
			pstmt.setString(index++, comment.getUser().getUsername());
			pstmt.setLong(index++, comment.getVideo().getId());
			pstmt.setString(index++, comment.getContent());
			pstmt.setDate(index++, comment.getDate());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new user in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;	
	}

}