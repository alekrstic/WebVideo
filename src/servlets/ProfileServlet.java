package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.UserLoginDTO;
import model.User;
import model.User.UserType;
import model.Video;
import services.UserService;
import services.VideoService;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/api/profile/*")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService userService;
	private VideoService videoService;
	
    public ProfileServlet() {
    	this.userService = new UserService();
        this.videoService = new VideoService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getPathInfo().substring(1);
		
		User user = userService.getUser(username);
		if (user == null) {
			response.setStatus(404);
			return;
		}

		List<Video> videos = videoService.getVideosByUser(user.getUsername());
		List<Video> videosFilter;
		UserLoginDTO loggedUser = (UserLoginDTO)request.getSession().getAttribute("loggedUser");

		if( loggedUser != null && loggedUser.getUserType() == UserType.ADMIN) {
			videosFilter = videos;
		} else {
			List<Video> videosFilterDeleted = videos.stream().filter(v-> !v.isDeleted()).collect(Collectors.toList());
			videosFilter = videosFilterDeleted;
			if(loggedUser == null || !loggedUser.getUsername().equals( username)) {
				videosFilter = videosFilterDeleted.stream().filter(v-> !v.isBlocked()).collect(Collectors.toList());
			}
		}
				
		HashMap<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("videos", videosFilter);
		
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(map);
		
		response.getWriter().write(json);
		response.setStatus(200);

	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getPathInfo().substring(1);	

		User oldUser = userService.getUser(username);
		if (oldUser == null) {
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
		User updateUser;
		

		try {
			updateUser = mapper.readValue(body, User.class);
			
		}catch (Exception e) {
			System.out.println("Greska");
			response.setStatus(400);
			return;
		}
		updateUser.setUsername(username);
		userService.updateUser(updateUser);
		
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getPathInfo().substring(1);	
		
		UserLoginDTO loggedUser = (UserLoginDTO)request.getSession().getAttribute("loggedUser");
		if(loggedUser == null || loggedUser.getUserType() != UserType.ADMIN) {
			//UNAUTORIZED
			response.setStatus(401);
			return;
		}
		userService.deleteUser(username);
		response.setStatus(200);
		
	}
	}


