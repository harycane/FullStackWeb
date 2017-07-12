<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Create Customer</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
      <div class = "panel">
			<h2>Existing Customers</h2>
            <div class="panel-history">
                <div class="table" id ="buyFundViewHis" >
                <table style="overflow-x:auto;">
                <tbody>
                    <tr>
                        
                        <th><b> FirstName </b></th>
                        <th><b> LastName </b></th>
                        <th><b> UserName </b></th>
                    </tr>
				<c:forEach var="cust" items="${cusList}">
				<tr>
						<td>${cust.firstname}</td>
						<td>${cust.lastname}</td>
						<td>${cust.username}</td>
				</tr>
				</c:forEach>
				</tbody>
                </table>
                </div> 
            </div>
            </div>
      <div class="center-form">
		  <h2>Create Customer</h2>
		  <c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		  </c:forEach>
		  <form action="createCustomer.do" method="POST">
				<p>
					First Name:
					<input type="text" name="firstname"
						value="${form.firstname}" autofocus />
				</p>
				<p>Last Name:
					<input type="text" name="lastname" value="${form.lastname}"
						autofocus />
				</p>
				<p>
					Username:
					<input type="text" name="username" value="${form.username}"
						autofocus />
				</p>
				<p>
					Address Line 1:
					<input type="text" name="addrline1"
						value="${form.addrline1}" autofocus />
				</p>
				<p>
					Address Line 2:
					<input type="text" name="addrline2"
						value="${form.addrline2}" autofocus />
				</p>
				<p>
					City:
					<input type="text" name="city" value="${form.city}"
						autofocus />
				<p>
					State:
					<input type="text" name="state" value="${form.state}"
						autofocus />
				</p>
				<p>
					Zip:
					<input type="text" name="zip" value="${form.zip}" autofocus />
				</p>
	
				<p>
					Password:
					<input type="password" name="password" />
				</p>
				<p>
					Confirm Password:
					<input type="password" name="confirmpassword" />
				</p>
				<p>
					Initial Deposit:
					<input type="text" name="cash" value="${form.cash}"
						autofocus />
				</p>
				<input class="formBtn" type="submit" name="action" value="Create" />
		</form>
	</div>
	</main>
	<jsp:include page="footer.jsp" />
</div>
