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
import model.User.UserType;
import services.ProfileService;

/**
 * Servlet implementation class ProfilesServlet
 */
@WebServlet("/api/profiles")
public class ProfilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ProfileService profileService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilesServlet() {
    	 this.profileService = new ProfileService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	UserLoginDTO loggedUser = (UserLoginDTO)request.getSession().getAttribute("loggedUser");
	if(loggedUser == null || loggedUser.getUserType() != UserType.ADMIN) {
		//UNAUTORIZED
		response.setStatus(401);
		return;
	}
	List<User> users = profileService.getProfiles();
	if(users == null) {
		response.getWriter().write("[]");
		return;
	}
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonData = mapper.writeValueAsString(users);
	
	response.getWriter().write(jsonData);
	response.setStatus(200);
}
}
