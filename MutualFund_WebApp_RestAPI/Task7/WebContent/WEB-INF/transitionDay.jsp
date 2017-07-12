<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Transition Day</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
         <div class = "panel">
<h2>Transition Operation</h2>
	
	
			<c:forEach var="error" items="${errors}">
				<p><h3 style="color:red"> ${error} </h3></p>
			</c:forEach>
		
		<form action="transitionDay.do" method="POST">
			<p style="font-size: large"> Last Transition Date: &ensp;
		                ${lastDate}
		           </p>
			<p style="font-size: large"> Date: &emsp;
		                
		                <input type="date" name="date" min="${lastDateForCal}" value="${date}" placeholder="MM/DD/YYYY" autofocus />
		           </p><br>
		           
		           
			<div class="panel-history" >
                <div class="table" id = "transitionTable" >
			<table style="overflow-x:auto">
				<tr>
					<th><b> Fund Symbol </b></th>
					<th><b> Fund Name </b></th>
					<th><b> Price ($)</b></th>
				</tr>
				<c:if test="${!(empty funds)}">
					<c:forEach var="fund" items="${funds}">
						<tr>
							<td>${fund.symbol}</td>
							<td>${fund.name}</td>
							<td><input type="text" name="${fund.fundid}" value="" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			</div>
		</div>
			<input class="formBtn" type="submit" name="action" value="Process" />
		</form>
		
	</div>
	
	
</div>
</div>

<jsp:include page="footer.jsp" />