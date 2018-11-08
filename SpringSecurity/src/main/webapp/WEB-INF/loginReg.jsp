<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Login/Registration</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<style>
		.red{
			color: #a94442;
		}
	</style>
</head>

<body>
	<div class="container">
	
		<c:if test="${logoutMsg != null}">
			<div class="alert alert-success alert-dismissable fade in">
	    		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	    		<c:out value="${logoutMsg}"/>
	    	</div>
		</c:if>
		
		<c:if test="${createdMsg != null && errorMsg == null}">
			<div class="alert alert-success alert-dismissable fade in">
    			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    			<c:out value="${createdMsg}" />
    		</div>
		</c:if>

		<h1>Login</h1>
		
		<c:if test="${errorMsg != null}">
			<div class="alert alert-danger alert-dismissable fade in">
	    		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	    		<c:out value="${errorMsg}"/>
	    	</div>
		</c:if>
	
		<form method="POST" action="/login" >
			<div class="form-group">
				<!-- label must be "username" for login for Spring Security -->
				<label for="username">Email:</label>
				<input type="text" id="username" name="username" class="form-control"/>
			</div>
			<div class="form-group">
				<label for="password">Password:</label>
				<input type="password" id="password" name="password" class="form-control"/>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="submit" value="Login" class="btn btn-default"/>
		</form>
		
		<h1>Register</h1>
		
		<form:errors path="user.*" class="red"/>
	
		<form:form method="POST" action="/registration" modelAttribute="user">
			<div class="form-group">
				<form:label path="email">Email:</form:label>
				<form:input path="email" class="form-control"/>
			</div>
			<div class="form-group">
				<form:label path="firstName">First Name:</form:label>
				<form:input path="firstName" class="form-control"/>
			</div>
			<div class="form-group">
				<form:label path="lastName">Last Name:</form:label>
				<form:input path="lastName" class="form-control"/>
			</div>
			<div class="form-group">
				<form:label path="password">Password:</form:label>
				<form:password path="password" class="form-control" />
			</div>
			<div class="form-group">
				<form:label path="passwordConfirmation">Password Confirmation:</form:label>
				<form:password path="passwordConfirmation" class="form-control"/>
			</div>
			<input type="submit" value="Register" class="btn btn-default"/>
		</form:form>
	</div>
</body>
</html>