package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.CommentDTO;
import dto.UserLoginDTO;
import model.User.UserType;
import model.Video;
import services.CommentService;
import services.LikeDislikeService;
import services.VideoService;

/**
 * Servlet implementation class VideoServlet
 */
@WebServlet("/api/videos/*")
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private VideoService videoService;
	private CommentService commentService;
	private LikeDislikeService likeDislikeService;
	
    public VideoServlet() {
    	this.likeDislikeService = new LikeDislikeService();
        this.videoService = new VideoService();
        this.commentService = new CommentService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo().substring(1);
		Long id;
		try {
			id = Long.parseLong(path);
			
		}catch(Exception e){
			response.setStatus(404);
			return;
		}
		Video video = videoService.getVideo(id);
		
		if (video == null) {
			response.setStatus(404);
			return;
		}
		UserLoginDTO user = (UserLoginDTO)request.getSession().getAttribute("loggedUser");
		video.setViews(video.getViews() +1);
		videoService.updateVideoViews(video);
		
		List<String> likes = likeDislikeService.getAllLikesByVideo(id, true);
		List<String> dislikes = likeDislikeService.getAllLikesByVideo(id, false);
		System.out.println(video.isEnabledComments());
		List<CommentDTO> comments = new ArrayList<>();
		if (video.isEnabledComments() || (user != null && user.getUsername() == video.getUser()) || (user != null && user.getUserType() == UserType.ADMIN)) {
			comments = commentService.getCommentsByVideo(id);
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonVideo = mapper.writeValueAsString(video);
		String jsonLikes = mapper.writeValueAsString(likes);
		String jsonDislikes = mapper.writeValueAsString(dislikes);
		String jsonComments = mapper.writeValueAsString(comments);
		String json = jsonVideo.substring(0, jsonVideo.length()-1);
		json += ",\"likes\":" + jsonLikes;
		json += ",\"dislikes\":" + jsonDislikes;
		json += ",\"comments\":" + jsonComments + "}";
		response.setContentType("application/json");
		response.getWriter().write(json);
		response.setStatus(200);
	}
	

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo().substring(1);
		Long id = null;
		try {
			id = Long.parseLong(path);
			
		}catch(Exception e){
			response.setStatus(404);
			return;
		}
		
		UserLoginDTO user = (UserLoginDTO)request.getSession().getAttribute("loggedUser");
		if (user == null) {
			//UNAUTORIZED
			response.setStatus(401);
			return;
		}
		if (user.getUserType() == UserType.ADMIN) {
			videoService.deleteVideo(id);
		}
		else {
			Video video = videoService.getVideo(id);
			video.setDeleted(true);
			videoService.updateVideoDelete(video);
		}
		response.setStatus(200);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo().substring(1);
		Long id = null;
		try {
			id = Long.parseLong(path);
			
		}catch(Exception e){
			response.setStatus(404);
			return;
		}
		BufferedReader reader = request.getReader();
        String body = "";
        String line;
        while( (line = reader.readLine()) != null ){
            body += line;
        }
        
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Video updateVideo;
		

		try {
			updateVideo = mapper.readValue(body, Video.class);
			
		}catch (Exception e) {
			System.out.println("Greska");
			response.setStatus(400);
			return;
		}
		Video oldVideo = videoService.getVideo(id);
		UserLoginDTO user = (UserLoginDTO)request.getSession().getAttribute("loggedUser");
		if (user == null || (user != null && !oldVideo.getUser().equals(user.getUsername()))) {
			//UNAUTORIZED
			response.setStatus(401);
			return;
		}
		updateVideo.setId(id);
		videoService.updateVideo(updateVideo);
		//OK
		response.setStatus(200);
		
	}


}

