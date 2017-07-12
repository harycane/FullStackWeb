<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>View Transaction History</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
         <div class = "panel">
		<h2>Customer List:</h2>
		
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
		<div class="panel-profile">
			<div class="content">
		<form action="viewTransList.do" method="POST">
			<table>
				<c:forEach var="customer" items="${cusTransList}">
					<p>
						<a href="viewTransByEmp.do?customerTransId=${ customer.customerid}">${customer.firstname} ${customer.lastname}</a>
					</p>
				</c:forEach>
			</table>
		</form>
	</div>
		</div>

	</div>
</div>
<jsp:include page="footer.jsp" />