<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Carnegie Financial Services</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
		<div class ="container_bg">
			  <img src="homepage.gif" alt="">
		</div>
		
		<div class="panel-center" id="loginForm">
	       <h2>Customer Login</h2>
	       <c:forEach var="error" items="${errors}">
		   <h3 style="color: red">${error}</h3>
	       </c:forEach>
		   <form method="post" action="customerLogin.do">
				<p>
					User Name: <input type="text" name="username"
						value="${form.username }" autofocus>
				</p>
				<p>
					Password: <input type="password" name="password">
				</p>

				<!--  input class="formBtn" type="submit" name="action" value="Go Back"-->
				<input class="formBtn" type="submit" name="action" value="Login" autofocus>
			</form>
			<div class="panel-center1">
			 <div class = "loginBtn" id = "empLogin">
			   <form action = "employeeLogin.do">
			    <input type = "submit" value = "Employee Login" >
			   </form>
			 </div>
			 <div class="loginBtn" id= "cusLogin">
			   <form action = "customerLogin.do">
			    <input type = "submit" value = "Customer Login" >
			   </form>
			 </div>
			
        </div>
     </div>
<jsp:include page="footer.jsp" />