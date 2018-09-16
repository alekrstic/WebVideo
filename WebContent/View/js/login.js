//OPEN LOGIN MODAL
function openLogin(){
		$('#signup-modal').modal('hide');
		$('#login-modal').modal('show');
	}

//LOGIN (HOME)
function login(){

	var split = window.location.pathname.split('/');
	var videoID = split[split.length-1];
	
	const loginURL = '/WebVideo/loginConfirm';

	let body = {};
	body.username = $('#loginUsername').val();
	body.password = $('#loginPassword').val();

	console.log(JSON.stringify(body));
	
	$.ajax
    ({
        type: "POST",
        url: loginURL,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(body),
		success: function(data,status){
			$('#login-modal').modal('hide');	
			$('#login-modal form :input').val("");
			location.reload();				
		},
		error: function (xhr,status,error) {

        	alert(status);
        }
    })
}

//OPEN REGISTER/SIGNUP MODAL
function openSignup(){
		$('#login-modal').modal('hide');
		$('#signup-modal').modal('show');
	}

//REGISTER
function register(){

	const registerURL = '/WebVideo/registerConfirm';
	
	let body = {};
	body.firstName = $('#firstName').val();
	body.lastName = $('#lastName').val();
	body.email = $('#email').val();
	body.username = $('#username').val();
	body.password = $('#password').val();
	
	$.ajax
    ({
        type: "POST",
        url: registerURL,
        contentType: 'application/json',
        data: JSON.stringify(body),
		success: function(data,status){
			$('#signup-modal').modal('hide');
			$('#signup-modal form :input').val("");
			$('#login-modal').modal('show');
			
		},
		error: function (xhr,status,error) {

        	alert("Error!"+status);
        }
    })
}

//LOGOUT
function logout(){
		$.ajax
	    ({
	        type: "POST",
	        url: "/WebVideo/logout",
			success: function(data,status){
				sessionStorage.clear();
				window.location.href = '/WebVideo/home';
			}
	    })
		
	}







