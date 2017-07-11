<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <!DOCTYPE html>
<html>
    <head>
        <title>Carnegie Financial Services</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body> --%>
    <header>
    	<div id="header">
    	<div class="logo">
        <h3>Carnegie Financial Services</h3>
    	</div>
        <div class= "showLogin">
        		<c:choose>
					<c:when test="${empty customer && empty employee}">
						<h3>Please Login!</h3>
					</c:when>
					
					<c:when test="${!(empty employee)}">
						<h3>Welcome! ${employee.firstname} ${employee.lastname}</h3>
					</c:when>
					<c:when test="${!(empty customer)}">
						<h3>Welcome! ${customer.firstname} ${customer.lastname} (${customer.username})</h3>
					</c:when>
				</c:choose>
			</div>
    	</div>
    </header>