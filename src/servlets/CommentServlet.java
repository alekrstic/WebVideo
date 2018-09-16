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

import dto.UserLoginDTO;
import model.Comment;
import services.CommentService;
import services.VideoService;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/api/comments")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	private CommentService commentService;
	private VideoService videoService;
   
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
        String body = "";
        String line;
        while((line = reader.readLine()) != null){
            body += line;
        }
		ObjectMapper mapper = new ObjectMapper();
		Comment newComment;

		try {
			newComment = mapper.readValue(body, Comment.class);
		}catch (Exception e) {
			System.out.println("greska1");
			//BAD REQUEST
			response.setStatus(400);
			return;
		}
		
		if(    StringUtils.isNullOrEmpty(newComment.getContent()) ||
			   StringUtils.isNullOrEmpty(newComment.getUser().getUsername()) ||
			   newComment.getVideo().getId() == null
		){
				System.out.println("greska2");
				//BAD REQUEST
				response.setStatus(400);
				return;
		}
		
		UserLoginDTO user = (UserLoginDTO)request.getSession().getAttribute("loggedUser");
		
		if( user == null || videoService.getVideo(newComment.getVideo().getId()) == null)
		{
			//FORBIDDEN
			response.setStatus(403);
			return;
		}
		
		commentService.addComment(newComment);
		//CREATED
		response.setStatus(201);
	}

}
