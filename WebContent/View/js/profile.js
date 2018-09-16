window.onload = function() {

	
    $.ajax
    ({
        type: "GET",
        url: "/WebVideo/isLogged",
        dataType: "json",
        async: false,
        success: function(data,status){
    	    if(data.status == false){
                    document.getElementById("logout").style.display = "none";


                }else{
                    sessionStorage.setItem("loggedUser", data.user);
                    sessionStorage.setItem("userType", data.userType);
                }

        }
    })
      
    var split = window.location.pathname.split('/');
    var profileID = split[split.length-1];
    var profileURL = 'http://localhost:8080/WebVideo/api/profile/'+profileID;
    
    $.ajax
    ({
        type: "GET",
        url: profileURL,
        dataType: "json",
        success: function(data,status){
            document.getElementById("userInfo").innerHTML = data.user.firstName + " " + data.user.lastName;
            document.getElementById("username").innerHTML = data.user.username;
            document.getElementById("descriptionLabel").innerHTML = data.user.description;

            sessionStorage.setItem("profile", JSON.stringify(data.user));
            let loggedUsername = sessionStorage.getItem("loggedUser");
            let userType = sessionStorage.getItem("userType");
            if(userType == "ADMIN"){
                if(data.user.blocked){
                    document.getElementById("blockUserButton").innerHTML = "Unblock";
                }

            }else{
                document.getElementById("blockUserButton").style.display = "none";
            }
            if(data.user.username != loggedUsername) {
                document.getElementById("editButton").style.display = "none";
            }
            if(data.user.username == loggedUsername) {
        	    document.getElementById("subscribeButton").style.display = "none";
            }
          
            data.videos.forEach(function(video, index, arr){
                var videoList = document.getElementById("videoList");
                var li = document.createElement("LI"); 
                var a = document.createElement("A");
                var img = document.createElement("IMG");
                
                a.href = "/WebVideo/videos/" + video.id; 
                img.src = video.imageURL;
                img.width = 50;
                img.heigth = 50;
                li.appendChild(a);
                a.appendChild(img);
                videoList.appendChild(li);
            })               
        }
    })
    
    var subscribersURL = 'http://localhost:8080/WebVideo/api/subscribe/'+profileID;
    $.ajax
    ({
        type: "GET",
        url: subscribersURL,
        dataType: "json",
        success: function(data,status){
    	data.forEach(function(user, index, arr){
    		if(user.username == loggedUsername){
    			document.getElementById("subscribeButton").style.display = "none";
    			return;
    		}
    	})
        }
    })
}
function openEdit() {
    $('#edit-modal').modal('show');

    let userString = sessionStorage.getItem("profile");
    let user = JSON.parse(userString);

    $("#firstName").val(user.firstName);
    $("#lastName").val(user.lastName);
    $("#password").val(user.password);
    $("#description").val(user.description);
}

function EditUser(){
    let username = sessionStorage.getItem("loggedUser");
    
    const updateUserURL = 'http://localhost:8080/WebVideo/api/profile/' + username;
    
    let body= {};
    body.username = username;
    body.firstName = $("#firstName").val();
    body.lastName = $("#lastName").val();
    body.password = $("#password").val();
    body.description = $("#description").val();
    

    $.ajax
            ({
                type: "PUT",
                url: updateUserURL,
                data: JSON.stringify(body),
                success: function(like ,status){
                    location.reload();
                    

                }
            })
}

function blockUser(){
    let userString = sessionStorage.getItem("profile");
    let user = JSON.parse(userString);
    
    const blockUserURL = 'http://localhost:8080/WebVideo/api/blockUser/' + user.username;    

    $.ajax
            ({
                type: "POST",
                url: blockUserURL,
                success: function(like ,status){
                    if(user.blocked){
                    document.getElementById("blockUserButton").innerHTML = "Block";

                    }else{
                        document.getElementById("blockUserButton").innerHTML = "Unblock";

                    }

                    

                }
            })
}

    
function subscribe(){
	
	var split = window.location.pathname.split('/');
    var profileID = split[split.length-1];
    var subscribeURL = 'http://localhost:8080/WebVideo/api/subscribe/'+profileID;

	$.ajax
    ({
        type: "POST",
        url: subscribeURL,
        success: function(data,status){
        	document.getElementById("subscribeButton").style.display = "none";
	    }
    })
}