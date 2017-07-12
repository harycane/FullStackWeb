<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Reset Customer Password</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
      <div class="center-form">
		<h2>Reset Customer's Password</h2>
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
		<form method="post" action="resetCustomerPassword.do">
			<input type="hidden" name="username" value="${form.username}">
			<p> Change password for: ${form.username} </p>
			<p>
				Password: <input type="password" name="password" autofocus>
			</p>
			<p>
				Confirm Password: <input type="password" name="confirmpassword">
			</p>
			<input class="formBtn" type="submit" name="action" value="Reset">
		</form>
	</div>
</main>
<jsp:include page="footer.jsp" />