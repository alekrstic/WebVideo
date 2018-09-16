package model;

import java.sql.Date;
import java.time.LocalDate;

public class Video {

	public enum Visibility {PUBLIC,UNLISTED,PRIVATE}
	private Long id;
	private String videoURL;
	private String imageURL;
	private String description;
	private Visibility visibility;
	private boolean enabledComments;
	private boolean visibleRating;
	private boolean blocked;
	private int views;
	private Date date;
	private String user;
	private boolean deleted;
	
	
	public Video() {
		this.date = Date.valueOf(LocalDate.now());
		this.views = 0;
		this.blocked=false;
		this.setDeleted(false);
	}
	
	//Koristimo prilikom service.Create POST
	public Video(String videoURL, String imageURL, Visibility visibility, boolean enabledComments, boolean visibleRating) {
		this.videoURL = videoURL;
		this.imageURL = imageURL;
		this.date = Date.valueOf(LocalDate.now());
		this.visibility = visibility;
		this.enabledComments = enabledComments;
		this.visibleRating = visibleRating;
		this.views = 0;
		this.blocked=false;
		this.setDeleted(false);
	}
	
	//Koristimo prilikom dobavljanja service GET
	public Video(Long id, String user, String VideoURL, String imageUrl, String description, Visibility visibility, boolean enabledComments,
				 boolean visibleRating, boolean blocked, int views, Date date, boolean deleted) {
		this.id = id;
		this.user = user;
		this.videoURL = VideoURL;
		this.imageURL = imageUrl;
		this.description = description;
		this.visibility = visibility;
		this.enabledComments = enabledComments;
		this.visibleRating = visibleRating;
		this.blocked = blocked;
		this.views = views;
		this.date = date;
		this.deleted = deleted;
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Visibility getVisibility() {
		return visibility;
	}
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	public boolean isEnabledComments() {
		return enabledComments;
	}
	public void setEnabledComments(boolean enabledComments) {
		this.enabledComments = enabledComments;
	}
	public boolean isVisibleRating() {
		return visibleRating;
	}
	public void setVisibleRating(boolean visibleRating) {
		this.visibleRating = visibleRating;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
	
}
