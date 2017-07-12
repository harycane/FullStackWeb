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
         <div class = "panel" id ="viewTransHis">
            <h2>Transaction History</h2>
            <p>Customer: ${customer.firstname} ${customer.lastname} 
            (User Name: ${customer.username} ) </p>
           <c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
            <div class="panel-history">
                <div class="table" >
                <table style="overflow-x:auto">
                    <tr>
                    	<th><b> Date </b></th>
                    	<th><b> Operation Type </b></th>
                        <th><b> Fund Name </b></th>
                        <th><b> Fund Symbol </b></th>
                        <th><b> Shares </b></th>
                        <th><b> Fund Price ($)</b></th>
                        <th><b> Amount ($)</b></th>
                       
                    </tr>
				<c:forEach var="TransByEmp" items="${transactionHistoryListByEmp}">
				<tr>
					<c:forEach var="history" items="${TransByEmp}">
						<td>${history}</td>
					</c:forEach>
				</tr>
				</c:forEach>
                </table>
                </div> 
            </div>
        </div>
    </div><jsp:include page="footer.jsp" />