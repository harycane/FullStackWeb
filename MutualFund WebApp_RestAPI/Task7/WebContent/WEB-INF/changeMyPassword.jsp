<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Change Password</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <c:if test="${!(empty employee)}">
	    <jsp:include page="employeeNav.jsp" />
        </c:if>
        <c:if test="${!(empty customer)}">
	    <jsp:include page="customerNav.jsp" />
        </c:if>
      <main>
      <div class="center-form">
		<h2>Change My Password</h2>
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
		<form method="post" action="changeMyPassword.do">
			<p>
				New Password: <input type="password" name="password" autofocus>
			</p>
			<p>
				Confirm Password: <input type="password" name="confirmpassword">
			</p>
			<input class="formBtn" type="submit" name="action" value="Change">
		</form>
	</div>
	</main>
	<jsp:include page="footer.jsp" />
</div>