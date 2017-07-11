<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <head>
        <title>Request Check</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="customerNav.jsp"/>
      <main>
      <div class="center-form">
			<h2>Check Request</h2>
			<c:forEach var="error" items="${errors}">
				<h3 style="color: red">${error}</h3>
			</c:forEach>
			<p>
				Available Cash Balance:     $ ${cash}
			</p>
			<form method="post" action="requestCheck.do">
				<p>
					Money Amount ($): <input type="text" name="amount" value="${form.amount}" autofocus>
				</p>
				<input class="formBtn" type="submit" name="action" value="Request">
			</form>
		</div>
</main>
<jsp:include page="footer.jsp" />