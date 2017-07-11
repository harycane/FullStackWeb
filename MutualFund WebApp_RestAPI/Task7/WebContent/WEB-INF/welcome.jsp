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
		<div class="panel-center">
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
	</body>
</html>