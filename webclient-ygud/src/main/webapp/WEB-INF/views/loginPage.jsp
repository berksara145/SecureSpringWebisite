<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
	<head>
		<title>Please Log In</title>
	</head>
	<body>
		<h1>Please Log In</h1>
		<div th:if="${param.error}">
			Invalid username and password.</div>
		<div th:if="${param.logout}">
			You have been logged out.</div>
		<form th:action="@{/login}" method="post" onsubmit="validateForm()">
			<div>
			<input type="text" id="username" name="username" placeholder="Username"/>
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
	    var firstName = document.getElementById("username").value;
	    var lastName = document.getElementById("password").value;

	    if (lastName.trim() === "" || firstName.trim() === "") {
	        alert("Name can't be empty'");
	        event.preventDefault(); // Prevent form submission
	        return false;
	    }

	    if (lastName.length < 6 || firstName.length < 6) {
	        alert("Must be longer than 6");
	        event.preventDefault(); // Prevent form submission
	        return false;
	    }

	    return true;
	}
 </script>