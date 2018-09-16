package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.UserLoginDTO;
import model.User;
import services.SubscribeService;
import services.UserService;

/**
 * Servlet implementation class SubscribeServlet
 */
@WebServlet("/api/subscribe/*")
public class SubscribeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService userService;
	private SubscribeService subscribeService;
	
    public SubscribeServlet() {
    	 userService = new UserService();
         subscribeService = new SubscribeService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String usernameSubscribed = request.getPathInfo().substring(1);	
		
		User user = userService.getUser(usernameSubscribed);
		if(user == null) {
			response.setStatus(404);
			return;
		}
	
		
		List<User> users = subscribeService.getAllSubscribersByUser(usernameSubscribed);
		ObjectMapper mapper = new ObjectMapper();
		String json  = mapper.writeValueAsString(users);
		response.getWriter().write(json);
		response.setStatus(200);
		//OK
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserLoginDTO userSubscriber = (UserLoginDTO) request.getSession().getAttribute("loggedUser");
		String usernameSubscribed = request.getPathInfo().substring(1);	
		
		User user = userService.getUser(usernameSubscribed);
		if(user == null) {
			response.setStatus(404);
			//NOT FOUND
			return;
		}
	
		if(subscribeService.checkSubscribe(userSubscriber.getUsername(), usernameSubscribed)) {
			response.setStatus(301);
			//NOT MODIFIED
			return;
		}
		subscribeService.subscribe(userSubscriber.getUsername(), usernameSubscribed);
		response.setStatus(200);			
	}

}
