package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.UserLoginDTO;
import model.User;
import model.User.UserType;
import services.UserService;

/**
 * Servlet implementation class BlockUserServlet
 */
@WebServlet("/api/blockUser")
public class BlockUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private UserService userService;
    public BlockUserServlet() {
    	userService = new UserService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/**protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String username = request.getPathInfo().substring(1);
		
		UserLoginDTO user =(UserLoginDTO) request.getSession().getAttribute("loggedUser");
		
		if(user == null || user.getUserType() != UserType.ADMIN) {
			response.setStatus(401);
			return;
		}
		
		User blockUser = userService.getUser(username);
		
		userService.blockUser(username, !blockUser.isBlocked());
		
		
	}

}
