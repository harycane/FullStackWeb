<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Deposit Check</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
      <div class="center-form">
	<h2>Check Deposit</h2>
	<c:forEach var="error" items="${errors}">
		<h3 style="color: red">${error}</h3>
	</c:forEach>
	<form method="post" action="depositCheck.do">
		<input type="hidden" name="username" value="${form.username}">
		<p> Deposit a check for: ${form.username}
		<p>
			Money Amount ($): <input type="text" name="amount" value="${form.amount}" autofocus>
		</p>
		<input class="formBtn" type="submit" name="action" value="Deposit">
	</form>
</div>
</main>
<jsp:include page="footer.jsp" />