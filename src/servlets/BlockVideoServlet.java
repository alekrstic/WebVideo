package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.UserLoginDTO;
import model.User.UserType;
import model.Video;
import services.VideoService;

/**
 * Servlet implementation class BlockVideoServlet
 */
@WebServlet("/api/blockVideo/*")
public class BlockVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private VideoService videoService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlockVideoServlet() {
    	videoService = new VideoService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo().substring(1);
		Long id;
		try {
			id = Long.parseLong(path);
			
		}catch(Exception e){
			response.setStatus(404);
			return;
		}
		
		UserLoginDTO user = (UserLoginDTO) request.getSession().getAttribute("loggedUser");
		
		if(user == null || user.getUserType() != UserType.ADMIN) {
			response.setStatus(401);
			return;
		}
		
		Video blockVideo = videoService.getVideo(id);
		blockVideo.setBlocked(blockVideo.isBlocked());
		videoService.updateVideoBlock(blockVideo);
	}

}
