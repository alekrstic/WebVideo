package model;

import java.sql.Date;
import java.time.LocalDate;

public class Comment{
	
	private Long id;
	private String content;
	private Date date;
	private User user;
	private Video video;
	
	
	public Comment() {
		this.date = Date.valueOf(LocalDate.now());
	}
	public Comment(Long id, String content, User user, Video video) {
		super();
		this.id = id;
		this.content = content;
		this.user = user;
		this.video = video;
		this.date = Date.valueOf(LocalDate.now());
	}
	public Comment(Long id, String content, User user, Video video, Date date) {
		super();
		this.id = id;
		this.content = content;
		this.user = user;
		this.video = video;
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	
	
	
	
}
