package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.UserLoginDTO;

/**
 * Servlet implementation class IsLoggedServlet
 */
@WebServlet("/isLogged")
public class IsLoggedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    /*public IsLoggedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
*/
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<>();
		UserLoginDTO user = (UserLoginDTO) request.getSession().getAttribute("loggedUser");
		
		if(user != null) {
			data.put("status", true);
			data.put("user" , user.getUsername());
			data.put("userType", user.getUserType());
		}else {
			data.put("status", false);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		PrintWriter writer = response.getWriter();
		writer.write(jsonData);
		response.setStatus(200);

	}


}
