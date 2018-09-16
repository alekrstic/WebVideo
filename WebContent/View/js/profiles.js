window.onload = function() {

	var loggedUsername;
    
    $.ajax
    ({
        type: "GET",
        url: "/WebVideo/isLogged",
        dataType: "json",
        success: function(data,status){
    	    if(data.status == false){
                    document.getElementById("logout").style.display = "none";


                }else{
                    sessionStorage.setItem("loggedUser", data.user);
                    console.log(JSON.stringify(data.user)); 
                    console.log(JSON.stringify(data)); 

                }

        }
    })
      
    const profilesURL = 'http://localhost:8080/WebVideo/api/profiles';
    
    $.ajax
    ({
        type: "GET",
        url: profilesURL,
        dataType: "json",
        success: function(data,status){  
                
            data.forEach(function(profile, index, arr){
                var profilesList = document.getElementById("profilesList");
                var li = document.createElement("LI"); 
                var a = document.createElement("A");
                var labUsername = document.createElement("LABEL");
                var labFirstName = document.createElement("LABEL");
                var labLastName = document.createElement("LABEL");
                var labEmail = document.createElement("LABEL");
                var labDate = document.createElement("LABEL");
                var btn = document.createElement("BUTTON");

                btn.innerHTML = "Delete";
                btn.addEventListener('click', function(){
                        deleteUser(profile.username);
                    })
                labFirstName.innerHTML = profile.firstName + "&nbsp;";
                labLastName.innerHTML = profile.lastName + "&nbsp;";
                labEmail.innerHTML = profile.email + "&nbsp;";

                var value = new Date(profile.date );
                var formattedDate = value.getMonth() + 1 + "-" + value.getDate() + "-" + value.getFullYear();
                
                labDate.innerHTML = formattedDate + "&nbsp;";
                console.log(JSON.stringify(profile));
                labUsername.innerText = profile.username;
                a.href = "/WebVideo/profile/" + profile.username; 
                a.appendChild(labUsername);
                li.appendChild(labFirstName);
                li.appendChild(labLastName);
                li.appendChild(labEmail);
                li.appendChild(labDate);
                li.appendChild(a);
                li.appendChild(btn);
                
                profilesList.appendChild(li);
            })
        },
            error: function(requestObject, error, errorThrown) {
                alert(error);
                alert(errorThrown);
            }
    })
}

function deleteUser(username){
    var deleteUserURL = 'http://localhost:8080/WebVideo/api/profile/' + username;

    $.ajax
    ({
        type: "DELETE",
        url: deleteUserURL,
        
        success: function(data,status){
            location.reload();  
        },
        error: function (xhr,status,error) {
            alert("Error!"+status);
        }
    })
}  

