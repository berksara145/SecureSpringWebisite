<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
	<head>
		<title>Please register</title>
	</head>
	<body>
		<h1>Register</h1>
		<p> ${errorMessage} </p>
		<form th:action="/register" method="post" onsubmit="validateForm()">
			<div>
			<input type="text" id="username" name="username" placeholder="Username"/>
			</div>
			<div>
			<input type="text" id="surname" name="surname" placeholder="Surname"/>
			</div>
			<div>
			<input type="text" id="role" name="role" placeholder="ADMIN, USER, GUEST"/>
			</div>
			<div>
			<input type="password" id="password" name="password" placeholder="Password"/>
			</div>
			<input type="submit" value="Log in" />
		</form>
	</body>
</html>
 <script>
 function validateForm() {
	    var username = document.getElementById("username").value;
	    var surname = document.getElementById("surname").value;
	    var role = document.getElementById("role").value;
	    var password = document.getElementById("password").value;

	    if (username.trim() === "" || surname.trim() === "") {
	        alert("Name can't be empty'");
	        event.preventDefault(); // Prevent form submission
	        return false;
	    }

	    if (username.length < 6 || surname.length < 6) {
	        alert("Must be longer than 6");
	        event.preventDefault(); // Prevent form submission
	        return false;
	    }

	    if (role != "ADMIN" && role != "USER" && role != "GUEST") {
	        alert("only available roles are USER, ADMIN and GUEST");
	        event.preventDefault(); // Prevent form submission
	        return false;
	    }
	    return true;
	}
 </script>