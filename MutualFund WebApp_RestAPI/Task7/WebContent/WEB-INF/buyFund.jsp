<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Buy Fund</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="customerNav.jsp"/>
      <main>
         <div class = "panel">
			<h2>Funds Available</h2>
			<div class="panel-profile">
				<div class="content">
					<p>
						Available Balance: $ ${cash}
					</p>
				</div>
			</div>
            <div class="panel-history">
            	<c:forEach var="error" items="${errors}">
            		<h3 style="color: red">${error}</h3>
            	</c:forEach>
                <div class="table" id ="buyFundViewHis" >
                <table style="overflow-x:auto;overflow-y:auto; max-height:900px">
                <tbody>
                    <tr>
                        <th><b> Fund Name </b></th>
                        <th><b> Fund Symbol </b></th>
                        <th><b> Money Amount ($)</b></th>
                        <th><b> Operation </b></th>
                    </tr>
				<c:forEach var="fund" items="${funds}">
				<tr>
						<td>${fund.name}</td>
						<td>${fund.symbol}</td>
						<form action = "buyFund.do" method="POST">
							<input type="hidden" name="symbol" value="${fund.symbol}" />
							<td><input type="text" name="amount" value="" /></td>
							<td id = "bsTd">
								<input class = "formBtn" type="submit" name ="action" value= "Buy">
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