window.onload = function() {
		var split = window.location.pathname.split('/');
		var videoID = split[split.length-1];
		var videoURL = 'http://localhost:8080/WebVideo/api/videos/'+videoID;
		var commentURL = 'http://localhost:8080/WebVideo/api/comments/';
		
		document.getElementById("likeVideoButton").addEventListener("click", function(){
			likeVideo(videoID);
		})
		
		document.getElementById("dislikeVideoButton").addEventListener("click", function(){
			dislikeVideo(videoID);
		})

		$.ajax
	    ({
	        type: "GET",
	        url: "/WebVideo/isLogged",
	        dataType: "json",
	        async: false,
			success: function(data,status){
				if(data.status == false){
					document.getElementById("logout").style.display = "none";
					document.getElementById("profile").style.display = "none";

				}else{
					document.getElementById("login").style.display = "none";
					document.getElementById("profileHref").innerHTML = data.user;
					document.getElementById("profileHref").href = "/WebVideo/profile/" + data.user;
					sessionStorage.setItem("loggedUser", data.user);
					sessionStorage.setItem("userType", data.userType);
				}
			}
	    })
		
	    $.ajax
	    ({
	        type: "GET",
	        url: videoURL,
	        dataType: "json",
			success: function(video,status){
				var ifVideo = document.getElementById("ifVideo");
				var a = getId(video.videoURL);
				ifVideo.src = 'http://www.youtube.com/embed/' + a;
				
				sessionStorage.setItem("video", JSON.stringify(video));
				
				//console.log(JSON.stringify(video.visibleRating));
				let username = sessionStorage.getItem("loggedUser");
				let userType = sessionStorage.getItem("userType");

	            if(userType == "ADMIN"){
	                if(video.blocked){
	                    document.getElementById("blockVideoButton").innerHTML = "Unblock";
	                }

	            }else{
	                document.getElementById("blockVideoButton").style.display = "none";
	            }

				if(username != video.user){
					document.getElementById("editVideoButton").style.visibility = 'hidden';
					if(userType != 'ADMIN'){
						document.getElementById("deleteVideoButton").style.visibility = 'hidden';
					}
				}

				
				
				if(video.enabledComments == false || sessionStorage.getItem("loggedUser") == null) {
					document.getElementById("commentInput").disabled = true;
					document.getElementById("postCommentButton").disabled = true;
					document.getElementById("postCommentButton").style.background = '#999';
				}
				else {
					document.getElementById("commentInput").disabled = false;
					document.getElementById("postCommentButton").disabled = false;
					document.getElementById("postCommentButton").style.background = '#007bff';
				}

				if (video.visibleRating == true) {
					document.getElementById("views").innerHTML = video.views + "  views";
				}
				else {
					document.getElementById("views").style.display = "none";
				}
				
				video.comments.forEach(function(comment , index, arr){
					var list = document.getElementById("commentList");
					var div = document.createElement("DIV");
					var li = document.createElement("LI");
					var a = document.createElement("A");


					div.classList.add("panel");
					div.classList.add("panel-heading");

					li.appendChild(div);

					var likeCommentButton = document.createElement("BUTTON");
					var dislikeCommentButton = document.createElement("BUTTON");

					var br = document.createElement("BR");
					var lab = document.createElement("LABEL");

					//console.log(JSON.stringify(comment));
					
					likeCommentButton.addEventListener('click', function(){
						likeComment(comment.id, this, dislikeCommentButton);
					})				
					dislikeCommentButton.addEventListener('click', function(){
						dislikeComment(comment.id, this, likeCommentButton);
					})

					likeCommentButton.classList.add("btn");
					likeCommentButton.classList.add("btn-success");
					likeCommentButton.classList.add("float-right");
					likeCommentButton.innerHTML = " Like ";

					dislikeCommentButton.classList.add("btn");
					dislikeCommentButton.classList.add("btn-danger");
					dislikeCommentButton.classList.add("float-right");
					dislikeCommentButton.innerHTML = " Dislike ";

					comment.likes.forEach(function(like, index, arr){
						if(like == sessionStorage.getItem("loggedUser")) {
							likeCommentButton.style.visibility = "hidden";
						}
					})

					comment.dislikes.forEach(function(dislike, index, arr){
						if(dislike == sessionStorage.getItem("loggedUser")) {
							dislikeCommentButton.style.visibility = "hidden";
						}
					})

					a.innerHTML = comment.username;
					a.href = "/WebVideo/profile/" + comment.username;

					li.appendChild(a);
					li.appendChild(br);

					lab.innerHTML = comment.content;

					li.appendChild(lab);
					li.appendChild(likeCommentButton);
					li.appendChild(dislikeCommentButton);
					list.appendChild(li);
				})

				
				video.likes.forEach(function(like, index, arr){
					if(like == sessionStorage.getItem("loggedUser")) {   
						document.getElementById("likeVideoButton").disabled = true;
						document.getElementById("dislikeVideoButton").disabled = false;
						document.getElementById("likeVideoButton").style.background = '#999';				
					}
				})

				video.dislikes.forEach(function(dislikes, index, arr){
					if(dislikes == sessionStorage.getItem("loggedUser")) {
						document.getElementById("dislikeVideoButton").disabled = true;
						document.getElementById("likeVideoButton").disabled = false;
						document.getElementById("dislikeVideoButton").style.background = '#999';
					}
				})
			},
	    	error: function(requestObject, error, errorThrown) {

	        	alert(error);
	        	alert(errorThrown);
	        }
	    })
	}


function deleteVideo(){
	var split = window.location.pathname.split('/');
	var videoID = split[split.length-1];
	var videoURL = 'http://localhost:8080/WebVideo/api/videos/'+videoID;

	$.ajax
    ({
        type: "DELETE",
        url: videoURL,
        
		success: function(data,status){
			window.location.href = '/WebVideo/home';
			
		},
		error: function (xhr,status,error) {

        	alert("Error!"+status);
        }
    })
}
//EDIT VIDEO
function editVideo(id){
	$('#myModal').modal('show');
	var video = sessionStorage.getItem("video");
	var obj = JSON.parse(video);
	$("#videoURL").val(obj.videoURL);
	$("#imageURL").val(obj.imageURL);
	$("#description").val(obj.description);
	$("#visibility").val(obj.visibility).change();
	$("#comments").prop("checked", !obj.enabledComments);
	$("#viewRating").prop("checked", !obj.visibleRating);
	//console.log(JSON.stringify(video));
}

function updateVideo(){
	var split = window.location.pathname.split('/');
	var videoID = split[split.length-1];
	var updateVideoURL = 'http://localhost:8080/WebVideo/api/videos/' + videoID;

	let body= {};
	body.videoURL = $("#videoURL").val();
	body.imageURL = $("#imageURL").val();
	body.description = $("#description").val();
	body.visibility = $("#visibility").val();
	body.enabledComments = !$('#comments').is(':checked');
	body.visibleRating = !$("#viewRating").is(':checked');

	$.ajax
	        ({
	            type: "PUT",
	            url: updateVideoURL,
	            data: JSON.stringify(body),
	            success: function(like ,status){
					location.reload();
					

	    		}
	        })
}

function getId(url) {
	
    var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    var match = url.match(regExp);

    if (match && match[2].length == 11) {
        return match[2];
    } else {
        return 'error';
    }
}



//LIKE VIDEO
function likeVideo(id){
	
	var isLikedURL = 'http://localhost:8080/WebVideo/api/isliked';
	let body = {};
	let user = {};

	var split = window.location.pathname.split('/');
	var videoID = split[split.length-1];
	
	let video = {};
	video.id = videoID;
	body.video = video;
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
	            	document.getElementById("likeVideoButton").disabled = true;
					document.getElementById("dislikeVideoButton").disabled = false;
					document.getElementById("likeVideoButton").style.background = '#999';
					document.getElementById("dislikeVideoButton").style.background = '#d9534f';
	    		}
	        })
	}
			    
//DISLIKE VIDEO
function dislikeVideo(id){
	
	var isLikedURL = 'http://localhost:8080/WebVideo/api/isliked';
	let body = {};
	let user = {};

	var split = window.location.pathname.split('/');
	var videoID = split[split.length-1];
	
	let video = {};
	video.id = videoID;
	body.video = video;
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
					document.getElementById("dislikeVideoButton").disabled = true;
					document.getElementById("likeVideoButton").disabled = false;
					document.getElementById("dislikeVideoButton").style.background = '#999';
					document.getElementById("likeVideoButton").style.background = '#5cb85c';
					

	    		}
	        })	
    }

function blockVideo(){
    var split = window.location.pathname.split('/');
	var videoID = split[split.length-1];
	var blockVideoURL = 'http://localhost:8080/WebVideo/api/blockVideo/'+videoID;   

    $.ajax
            ({
                type: "POST",
                url: blockVideoURL,
                success: function(like ,status){

                	let videoString = sessionStorage.getItem("video");
                	let video = JSON.parse(videoString);

                    if(video.blocked){
                    	document.getElementById("blockVideoButton").innerHTML = "Block";
                    }else{
                        document.getElementById("blockVideoButton").innerHTML = "Unblock";
                    }
                }
            })
}