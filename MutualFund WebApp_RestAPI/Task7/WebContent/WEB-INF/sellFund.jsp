<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Sell Fund</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="customerNav.jsp"/>
      <main>
         <div class = "panel">
			<h2>Funds Owned</h2>
            <div class="panel-history">
                <c:forEach var="error" items="${errors}">
            		<h3 style="color: red">${error}</h3>
            	</c:forEach>
                <div class="table" id = "sellFundViewHis">
                <table style="overflow-x:auto; overflow-y:auto; max-height:900px">
                <tbody>
                    <tr>
                        <th><b> Fund Symbol </b></th>
                        <th><b> Number of Shares </b></th>
                        <th><b> Total Value ($) </b></th>
                        <th><b> Shares to Sell </b></th>
                        <th><b> Operation </b></th>
                    </tr>
					<c:forEach var="fund" items="${fundsList}">
					<tr>
						<c:forEach var="info" items="${fund}">
							<td>${info}</td>
						</c:forEach>
						<form action = "sellFund.do" method="POST">
							<input type="hidden" name="symbol" value="${fund[0]}" />
							<td><input type="text" name="shares" value="" /></td>
							<td id = "bsTd">	
									<input class = "formBtn" type="submit" name ="action" value= "Sell">
							</td>
						</form>
					</tr>
					</c:forEach>
				</tbody>
                </table>
                </div> 
            </div>
        </div>
    </main>
<jsp:include page="footer.jsp" />
</div>