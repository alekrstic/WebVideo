package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;

import model.LikeDislike;
import services.LikeDislikeService;

/**
 * Servlet implementation class LikeDislikeServlet
 */
/*@WebServlet("/api/LikeDislikeServlet")*/
@WebServlet("/api/isliked")
public class LikeDislikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LikeDislikeService likeDislikeService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeDislikeServlet() {
    	this.likeDislikeService = new LikeDislikeService(); 
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = request.getReader();
        String body = "";
        String line;
        while((line = reader.readLine()) != null){
            body += line;
        }
		ObjectMapper mapper = new ObjectMapper();
		LikeDislike newLikeDislike;

		try {
			newLikeDislike = mapper.readValue(body, LikeDislike.class);
		}catch (Exception e) {
			System.out.println(" -= Error = objectMapper =- ");
			response.setStatus(400);
			return;
		}
		
		if(StringUtils.isNullOrEmpty(newLikeDislike.getUser().getUsername()) ||
		   newLikeDislike.getComment() == null && newLikeDislike.getVideo() == null
		  ){
				System.out.println(" -= Error = 1. You are not logged.  =-");
				response.setStatus(400);
				return;
		}
		
		boolean sucess = likeDislikeService.addLikeDislike(newLikeDislike);
		if (!sucess) {
			System.out.println(" -= Error = You cant Like twice one comment/video =- ");
			response.setStatus(400);
		}
		response.setStatus(200);
	}
	}


