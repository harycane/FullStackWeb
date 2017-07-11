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
        <jsp:include page="customerNav.jsp"/>
      <main>
         <div class = "panel">
			<h2>Transaction History</h2>
            <div class="panel-history">
                <div class="table" id ="viewTransHis">
                <table  style="overflow-x:auto ;overflow-y:auto; max-height:900px">
                <tbody>
                    <tr>
                    	<th><b> Transaction Date </b></th>
                    	<th><b> Operation Type </b></th>
                        <th><b> Fund Name </b></th>
                        <th><b> Fund Symbol </b></th>
                        <th><b> Shares </b></th>
                        <th><b> Fund Price ($)</b></th>
                        <th><b> Amount ($)</b></th>
                       
                    </tr>
				<c:forEach var="trans" items="${transactionHistoryList}">
				<tr>
					<c:forEach var="info" items="${trans}">
						<td>${info}</td>
					</c:forEach>
				</tr>
				</c:forEach>
				</tbody>
                </table>
                </div> 
            </div>
        </div>
    </div>
<jsp:include page="footer.jsp" />