package dto;

import java.util.ArrayList;
import java.util.List;

public class CommentDTO {
	
	private Long id;
	private String username;
	private Long videoID;
	private String content;
	private List<String> likes;
	private List<String> dislikes;
	
	public CommentDTO(){
		
		likes = new ArrayList<>();
		dislikes = new ArrayList<>();	
	}
	
	public CommentDTO(Long id, String username, Long videoID, String content, List<String> likes, List<String> dislikes) {
		super();
		this.id = id;
		this.username = username;
		this.videoID = videoID;
		this.content = content;
		this.likes = likes;
		this.dislikes = dislikes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getVideoID() {
		return videoID;
	}

	public void setVideoID(Long videoID) {
		this.videoID = videoID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getLikes() {
		return likes;
	}

	public void setLikes(List<String> likes) {
		this.likes = likes;
	}

	public List<String> getDislikes() {
		return dislikes;
	}

	public void setDislikes(List<String> dislikes) {
		this.dislikes = dislikes;
	}
	
	
	

}

