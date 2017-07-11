<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Create Fund</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
      <div class = "panel">
			<h2>Existing Funds</h2>
            <div class="panel-history">
                <div class="table" id ="buyFundViewHis" >
                <table style="overflow-x:auto;">
                <tbody>
                    <tr>
                        <th><b> Fund Name </b></th>
                        <th><b> Fund Symbol </b></th>
                    </tr>
				<c:forEach var="fund" items="${funds}">
				<tr>
						<td>${fund.name}</td>
						<td>${fund.symbol}</td>
				</tr>
				</c:forEach>
				</tbody>
                </table>
                </div> 
            </div>
        </div>
      <div class="center-form">
		<h2>Create Fund</h2>
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
		<form method="post" action="createFund.do">
			<p>
				Fund Name: <input type="text" name="name" value="${form.name}" autofocus>
			</p>
			<p>
				Fund Symbol: <input type="text" name="symbol" value="${form.symbol}">
			</p>
			<input class="formBtn" type="submit" name="action" value="Create">
		</form>
	</div>
</main>
<jsp:include page="footer.jsp" />
</div>