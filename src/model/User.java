package model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import java.util.ArrayList;


public class User {
	
	public enum UserType{ADMIN,USER}
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String description;
	private Date date;
	private UserType userType;
	private boolean blocked;
	private List<User> followers;
	private List<LikeDislike> videos;
	private List<LikeDislike> comments;
	
	public User() {
		this.blocked = false;
		this.date = Date.valueOf(LocalDate.now());
		followers = new ArrayList<>();
		videos = new ArrayList<>();
		comments = new ArrayList<>();
	}
	public User(String username, String password, String firstName, String lastName, String email, UserType userType) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userType = userType;
		this.date = Date.valueOf(LocalDate.now());
	}
	
	public User(String username, String password, String firstName, String lastName, String email,
			UserType userType, Boolean blocked, Date dateUser, String descrption) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userType = userType;
		this.blocked = blocked;
		this.date = dateUser;
		this.description = descrption;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	public List<LikeDislike> getVideos() {
		return videos;
	}
	public void setVideos(List<LikeDislike> videos) {
		this.videos = videos;
	}
	public List<LikeDislike> getComments() {
		return comments;
	}
	public void setComments(List<LikeDislike> comments) {
		this.comments = comments;
	}
	
	
	
}
