package model;

import java.sql.Date;
import java.time.LocalDate;

public class LikeDislike {

	private Long id;
	private boolean like;
	private Date date;
	private Comment comment;
	private Video video;
	private User user;
	
	public LikeDislike() {
		this.date = Date.valueOf(LocalDate.now());
	}
	
	public LikeDislike(Long id, boolean like, Comment comment, Video video, User user) {
		super();
		this.id = id;
		this.like = like;
		this.comment = comment;
		this.video = video;
		this.date = Date.valueOf(LocalDate.now());
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isLike() {
		return like;
	}
	public void setLike(boolean like) {
		this.like = like;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
