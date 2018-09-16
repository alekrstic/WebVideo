package services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import model.Video;
import model.Video.Visibility;

public class VideoService {
	
	public List<Video> getVideos() {
		Connection conn = ConnectionManager.getConnection();
		
		Statement s = null;
		try {
			String query = "SELECT * FROM Video WHERE visibility='PUBLIC' OR visibility='UNLISTED' AND blocked_video='0'";
						
			ArrayList<Video> videos = new ArrayList<>();
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while (rs.next()) {
				Long id = rs.getLong(1);
				String user = rs.getString(2);
				String videoUrl = rs.getString(3);
				String imageUrl = rs.getString(4);
				String description = rs.getString(5);
				String visibilty = rs.getString(6);
				boolean isEnabledComments = rs.getBoolean(7);
				boolean isVisibleRating = rs.getBoolean(8);
				boolean blocked = rs.getBoolean(9);
				int views = rs.getInt(10);
				Date date = rs.getDate(11);
				boolean deleted = rs.getBoolean(12);
				
				Video video = new Video(id, user, videoUrl, imageUrl, description, Visibility.valueOf(visibilty), isEnabledComments, isVisibleRating, blocked, views, date, deleted);
				videos.add(video);			
			}
			s.close();
			
			return videos;
						
			
		} catch (Exception e) {
			
		}
		
		return null;
	}
	
	public List<Video> getVideosByUser(String username) {
		Connection conn = ConnectionManager.getConnection();
		Statement pstmt = null;
		
		List<Video> videos = new ArrayList<>();
		try {
			String query = "SELECT * FROM Video WHERE deleted=false AND username='" + username + "'";
			
			pstmt = conn.createStatement();
			ResultSet rs = pstmt.executeQuery(query);
			Video video=null;
			while (rs.next()) {
				Long videoId = rs.getLong(1);
				String user = rs.getString(2);
				String videoUrl = rs.getString(3);
				String imageUrl = rs.getString(4);
				String description = rs.getString(5);
				String visibilty = rs.getString(6); 
				boolean isEnabledComments = rs.getBoolean(7);
				boolean isVisibleRating = rs.getBoolean(8);
				boolean blocked = rs.getBoolean(9);
				int views = rs.getInt(10);
				Date date = rs.getDate(11);
				boolean deleted = rs.getBoolean(12);
				
				video = new Video(videoId, user, videoUrl, imageUrl, description, Visibility.valueOf(visibilty), isEnabledComments, isVisibleRating, blocked, views, date, deleted);
				videos.add(video);
	
			}
			pstmt.close();
			return videos;			
			
			
		} catch (Exception e) {
			
		}						
		return null;
	}
		
	public Video getVideo(Long id) {
		Connection conn = ConnectionManager.getConnection();
		Statement pstmt = null;
		try {
			String query = "SELECT * FROM Video WHERE videoID='" + id + "'";
			
			pstmt = conn.createStatement();
			ResultSet rs = pstmt.executeQuery(query);
			Video video=null;
			if (rs.next()) {
				Long videoId = rs.getLong(1);
				String user = rs.getString(2);
				String videoUrl = rs.getString(3);
				String imageUrl = rs.getString(4);
				String description = rs.getString(5);
				String visibilty = rs.getString(6);
				boolean isEnabledComments = rs.getBoolean(7);
				boolean isVisibleRating = rs.getBoolean(8);
				boolean blocked = rs.getBoolean(9);
				int views = rs.getInt(10);
				Date date = rs.getDate(11);
				boolean deleted = rs.getBoolean(12);
				
				video = new Video(videoId, user, videoUrl, imageUrl, description, Visibility.valueOf(visibilty), isEnabledComments, isVisibleRating, blocked, views, date, deleted);
	
			}
			pstmt.close();
			return video;			
			
			
		} catch (Exception e) {
			
		}						
		return null;
	}

	public boolean addVideo(Video video) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO Video (url, image, description, visibility, commentsEnabled, "
					+ "ratingVisibility, blocked_video, views, date_video, username, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, video.getVideoURL());
			pstmt.setString(index++, video.getImageURL());
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isEnabledComments());
			pstmt.setBoolean(index++, video.isVisibleRating());
			pstmt.setBoolean(index++, video.isBlocked());
			pstmt.setLong(index++, video.getViews());
			pstmt.setDate(index++, video.getDate());
			pstmt.setString(index++, video.getUser());
			pstmt.setBoolean(index++, video.isDeleted());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new video in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public boolean updateVideoDelete(Video video) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Video SET deleted=? WHERE videoID=?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setBoolean(index++, video.isDeleted());
			pstmt.setLong(index++, video.getId());
				
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new user in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public boolean updateVideoBlock(Video video) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Video SET blocked_video=? WHERE videoID=?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setBoolean(index++, video.isBlocked());
			pstmt.setLong(index++, video.getId());
				
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new video in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	public boolean updateVideoViews(Video video) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Video SET views=? WHERE videoID=?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setLong(index++, video.getViews());
			pstmt.setLong(index++, video.getId());
				
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while adding new video in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	public boolean updateVideo(Video video) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE Video SET url=?, image=?, description=?, visibility=?, commentsEnabled=?, "
					+ "ratingVisibility=? WHERE videoID=?";
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, video.getVideoURL());
			pstmt.setString(index++, video.getImageURL());
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isEnabledComments());
			pstmt.setBoolean(index++, video.isVisibleRating());
			pstmt.setLong(index++, video.getId());
			
		
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			System.out.println("Error while updating video in database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	
	public boolean deleteVideo(Long id) {		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM Video WHERE videoID=?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setLong(1, id);
			
			return pstmt.executeUpdate() == 1;

		} catch (SQLException ex) {
			System.out.println("Error while deleting video from database!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}		
}