package servlets;

import java.io.BufferedReader;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import model.User.UserType;
import services.UserService;;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/registerConfirm")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService userService;
	
    public RegisterServlet() {
    	userService = new UserService();
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
        String body = "";
        String line;
        while((line = reader.readLine()) != null){
            body += line;
        }
		ObjectMapper mapper = new ObjectMapper();
		User newUser;

		try {
			newUser = mapper.readValue(body, User.class);
		}catch (Exception e) {
			System.out.println("greska1");
			response.setStatus(400);
			return;
		}
		newUser.setUserType(UserType.USER);
		
		//dodati za email i username da su jedinstveni i ostalo
		if(    newUser.getUsername() == null || newUser.getUsername().equals("")
			|| newUser.getPassword() == null || newUser.getPassword().equals("")
			|| newUser.getEmail() == null || newUser.getEmail().equals("")
		){
			System.out.println("greska2");
			response.setStatus(400);
			
			return;
		}
		System.out.println("Uspesna registracija");
		userService.addUser(newUser);
		response.setStatus(201);
			
	}

}
