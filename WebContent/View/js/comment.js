//POST COMMENT
function postComent(){
	
    var commentURL = 'http://localhost:8080/WebVideo/api/comments';
	
	var split = window.location.pathname.split('/');
	var videoID = split[split.length-1];
	let video = {};
	video.id = videoID;
	let body = {};
	body.video = video;


	let user = {};
	user.username = sessionStorage.getItem('loggedUser');
	body.user = user;
	body.content = $('#commentInput').val();
	
	

	$.ajax
    ({
        type: "POST",
        url: commentURL,
        data: JSON.stringify(body),
        success: function(comment ,status){
        	var list = document.getElementById("commentList");
			var li = document.createElement("LI");
			var a = document.createElement("A");
			var br = document.createElement("BR");
			var lab = document.createElement("LABEL");
			
			a.innerHTML = sessionStorage.getItem('loggedUser');
			a.href = "/WebVideo/profile/" + sessionStorage.getItem('loggedUser');

			li.appendChild(a);
			li.appendChild(br);

			lab.innerHTML = body.content;
			li.appendChild(lab);
			list.appendChild(li);

			$('#commentInput').val("");

		}
    })   	
}
//LIKE COMMENT
function likeComment (id,event, dislikeButton){
	
	var isLikedURL = 'http://localhost:8080/WebVideo/api/isliked';
	let body = {};
	let user = {};
	
	let comment = {};
	comment.id = id;
	body.comment = comment;
	body.like = true;
	
	user.username = sessionStorage.getItem('loggedUser');
	body.user = user;
	
	console.log(JSON.stringify(body));

	$.ajax
	        ({
	            type: "POST",
	            url: isLikedURL,
	            data: JSON.stringify(body),
	            success: function(like ,status){
	            	console.log(event);
	            	dislikeButton.style.visibility = "visible";
	            	event.style.visibility = "hidden";
	    		}
	        })
}


//DISLIKE COMMENT
function dislikeComment(id,event, likeButton){
		var isLikedURL = 'http://localhost:8080/WebVideo/api/isliked';
		let body = {};
		let user = {};
		
		let comment = {};
		comment.id = id;
		body.comment = comment;
		body.like = false;
		
		user.username = sessionStorage.getItem('loggedUser');
		body.user = user;
		
		console.log(JSON.stringify(body));

		$.ajax
		        ({
		            type: "POST",
		            url: isLikedURL,
		            data: JSON.stringify(body),
		            success: function(like ,status){
		            	console.log(event);
		            	likeButton.style.visibility = "visible";
		            	event.style.visibility = "hidden";
		    		}
		        })
}