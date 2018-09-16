package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.UserLoginDTO;
import services.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginConfirm")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService userService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        userService = new UserService();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader =request.getReader();
        String body = "";
        String line;
        while( (line = reader.readLine()) != null ){
            body += line;
        }
		ObjectMapper mapper = new ObjectMapper();
		UserLoginDTO user;
		try {
			user = mapper.readValue(body, UserLoginDTO.class);
		}catch (Exception e) {
			response.setStatus(400);
			return;
		}
		if(user.getUsername() ==null || user.getUsername() .equals("")|| 
		   user.getPassword()  == null || user.getPassword() .equals("")){
				response.setStatus(400);
				return;
		}
		
		boolean validUser = userService.checkUser(user);
		if(!validUser) {
			response.sendError(400);
			return;
		}
				
		request.getSession().setAttribute("loggedUser", user);
		response.setStatus(200);
		
	}

}
