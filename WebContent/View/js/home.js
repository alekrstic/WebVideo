window.onload = function() {
	//GET LOGIN
	$.ajax
    ({
        type: "GET",
        url: "isLogged",
        dataType: "json",
		success: function(data,status){
			if(data.status == false){
				document.getElementById("profileList").style.display = "none";
				document.getElementById("logout").style.display = "none";
				document.getElementById("profile").style.display = "none";
				document.getElementById("shareButton").style.display = "none";

			}else{
				document.getElementById("login").style.display = "none";
				document.getElementById("profileHref").innerHTML = data.user;
				document.getElementById("profileHref").href = "/WebVideo/profile/" + data.user;

				if(data.userType != "ADMIN"){
					document.getElementById("profileList").style.display = "none";
				}
			}
		}
    })
    
    //GET VIDEOS
    $.ajax
    ({
       type: "GET",
        url: "videos",
        dataType: "json",
		success: function(data,status){
			data.forEach(function(video, index, arr){
				var videoList = document.getElementById("videoList");
				var liVid = document.createElement("LI"); 
				var aVid = document.createElement("A");
				var img = document.createElement("IMG");
				var liProfile = document.createElement("LI"); 
				var lab = document.createElement("LABEL");
				var br = document.createElement ("BR");								

				aVid.href = "videos/" + video.id; 
				liVid.appendChild(aVid);	
				
				img.src = video.imageURL;
				img.width = 600;
				img.heigth = 800;				
				aVid.appendChild(img);
				liVid.appendChild(br);

				var value = new Date(video.date);
				var formattedDate = value.getMonth() + 1 + "-" + value.getDate() + "-" + value.getFullYear();

				lab.innerHTML = "<span style='color: #333;'>Posted by: </span>" 
								+ "<a href=\"/WebVideo/profile/ "+video.user+"\" style='color: #5cb85c;'>" + video.user +"</a>" 
								+ "<span style='color: #333;'> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Published on </span>"
								 + "<span style='color: #333;'>" + formattedDate; + "</span>";				
				
				liProfile.appendChild(lab);

				videoList.appendChild(br);
				videoList.appendChild(liVid);
				videoList.appendChild(aVid);
				videoList.appendChild(liProfile);
				videoList.appendChild(br);	
			})
		}
    })
};

//POST VIDEO/SHARE VIDEO/ADDING NEW VIDEO
function shareVideo(){

	const video = 'videos';
	const loginURL = 'login';
	
	let body = {};
	body.videoURL = $('#videoURL').val();
	body.imageURL = $('#imageURL').val();
	body.description = $('#description').val();
	body.visibility = $('#visibility').val();
	body.enabledComments = !$('#comments').is(':checked');
	body.visibleRating = !$("#viewRating").is(':checked');
			
	//console.log(JSON.stringify(body));
	
	$.ajax

    ({
        type: "POST",
        url: video,
        contentType: 'application/json',
        data: JSON.stringify(body),
		success: function(data,status){
			location.reload();
			
		},
		error: function (xhr,status,error) {

        	alert("Error!"+status);
        }
    })
}