<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>View Customer Account</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
         <div class = "panel">
            <h2>Customer's Profile</h2>
            <c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
            <div class="panel-profile">
                <div class="content">
                        <p>Name: ${custViewed.firstname} ${custViewed.lastname} </p>
                        <p>User Name: ${custViewed.username} </p>
                        <p>Address:
                        	<c:if test="${custViewed.addrline1 != null}">
								${custViewed.addrline1}
							</c:if>
							<c:if test="${custViewed.addrline2 != null}">
								${custViewed.addrline2}
							</c:if>
							<c:if test="${custViewed.city != null}">
								${custViewed.city}
							</c:if>
							<c:if test="${custViewed.state != null}">
								${custViewed.state}
							</c:if>
							<c:if test="${custViewed.zip != null}">
								${custViewed.zip}
							</c:if>
						</p>
                        <p>Last Trading Day: ${lastdate}</p>
                        <p>Cash Balance: $ ${cashBalance }</p> 
                </div> 
                <div class = "operation">
	                        <form action="resetCustomerPassword.do">
	                        	<input class = "formBtn" type = "submit" value = "Reset Password">
	                        	<input type="hidden" name="username" value="${custViewed.username}">
	                        </form>
	                        <form action="viewTransByEmp.do">
	                        	<input class = "formBtn" type = "submit" value = "View Transaction History">
	                        	<input type="hidden" name="username" value="${custViewed.username}">
	                        </form>
	                         <form action="depositCheck.do">
	                        	<input class = "formBtn" type = "submit" value = "Deposit Check">
	                        	<input type="hidden" name="username" value="${custViewed.username}">
	                        </form>
                 </div>
            </div>
            
            <h2>Funds Owned</h2>
            <div class="panel-history">
                <div class="table" id = "cusViewAcctTable" >
                <table style="overflow-x:auto">
                <tbody>
                    <tr>
                        <th><b> Fund Name </b></th>
                        <th><b> Fund Symbol </b></th>
                        <th><b> Number of Shares </b></th>
                        <th><b>Total Value </b></th>
                    </tr>
				<c:forEach var="fund" items="${fundsList}">
				<tr>
					<c:forEach var="info" items="${fund}">
						<td>${info}</td>
					</c:forEach>
				</tr>
				</c:forEach>
				</tbody>
                </table>
                </div> 
            </div>
            
        </div>
    </main>
<jsp:include page="footer.jsp" />