<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Create Employee</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="employeeNav.jsp"/>
      <main>
      <div class = "panel">
			<h2>Existing Employees</h2>
            <div class="panel-history">
                <div class="table" id ="buyFundViewHis" >
                <table style="overflow-x:auto;">
                <tbody>
                    <tr>
                        
                        <th><b> FirstName </b></th>
                        <th><b> LastName </b></th>
                        <th><b> UserName </b></th>
                    </tr>
				<c:forEach var="emp" items="${empList}">
				<tr>
						<td>${emp.firstname}</td>
						<td>${emp.lastname}</td>
						<td>${emp.username}</td>
				</tr>
				</c:forEach>
				</tbody>
                </table>
                </div> 
            </div>
            </div>
      <div class="center-form">
	<h2>Create Employee</h2>
	<c:forEach var="error" items="${errors}">
		<h3 style="color: red">${error}</h3>
	</c:forEach>
	<form action="createEmployee.do" method="POST">
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
				Password:
				<input type="password" name="password" />
			</p>
			<p>
				Confirm Password:
				<input type="password" name="confirmpassword" />
			</p>

			<input class="formBtn" type="submit" name="action" value="Create" />
	</form>
	</div>
	</main>
<jsp:include page="footer.jsp" />
</div>