package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;

import dto.UserLoginDTO;
import model.User.UserType;
import model.Video;
import services.VideoService;

/**
 * Servlet implementation class VideosServlet
 */
@WebServlet("/videos")
public class VideosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private VideoService videoService;
    
    public VideosServlet() {
    	videoService = new VideoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Video> videos = videoService.getVideos();
		if(videos == null) {
			response.getWriter().write("[]");
			return;
		}
		List<Video> videosFilter;
		UserLoginDTO user = (UserLoginDTO)request.getSession().getAttribute("loggedUser");

		if(user != null && user.getUserType() == UserType.ADMIN) {
			videosFilter = videos;
		} else {
			
			List<Video> videosFilterDeleted = videos.stream().filter(v-> !v.isDeleted()).collect(Collectors.toList());
			//Filtriram vfd listu
			videosFilter = videosFilterDeleted.stream().filter(v-> !v.isBlocked()).collect(Collectors.toList());
	}
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(videosFilter);
		
		response.getWriter().write(jsonData);
		response.setStatus(200);
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
        String body = "";
        String line;
        while( (line = reader.readLine()) != null ){
            body += line;
        }
        
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Video newVideo;
		

		try {
			newVideo = mapper.readValue(body, Video.class);
			
		}catch (Exception e) {
			System.out.println("Greska");
			response.setStatus(400);
			return;
		}
		
		
		if(    StringUtils.isNullOrEmpty(newVideo.getVideoURL())
			|| StringUtils.isNullOrEmpty(newVideo.getImageURL())
			|| newVideo.getVisibility() == null
		){
			System.out.println("greska");
			response.setStatus(400);
			return;
		}
		UserLoginDTO user = (UserLoginDTO)request.getSession().getAttribute("loggedUser");
		if (user == null) {
			//
			response.setStatus(401);
			return;
		}
		newVideo.setUser(user.getUsername());
		
		videoService.addVideo(newVideo);
		//CREATED
		response.setStatus(201);	
	}

}
