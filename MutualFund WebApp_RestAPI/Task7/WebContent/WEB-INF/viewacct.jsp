<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>View Account</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="customerNav.jsp" />
    <main>
        <div class="panel">
            <h2>Profile</h2>
            <div class="panel-profile">
                <div class="content">
                        <p>Name: ${customer.firstname} ${customer.lastname} </p>
                        <p>User Name: ${customer.username} </p>
                        <p>Address:
                        	<c:if test="${customer.addrline1 != null}">
								${customer.addrline1}
							</c:if>
							<c:if test="${customer.addrline2 != null}">
								${customer.addrline2}
							</c:if>
							<c:if test="${customer.city != null}">
								${customer.city}
							</c:if>
							<c:if test="${customer.state != null}">
								${customer.state}
							</c:if>
							<c:if test="${customer.zip != null}">
								${customer.zip}
							</c:if>
						</p>
                        <p>Last Trading Day: ${lastdate}</p>
                        <p>Available Cash Balance: $ ${cash}</p>
                </div> 
                <div class="operation">
                  <ul>
		                <li class="formBtn"><a href="changeMyPassword.do"> Change
								Password </a></li>
						<li class="formBtn"><a href="viewTransactionHistory.do">View
								Transaction History </a></li>
					</ul>
                </div>
            </div>
            
            <h2>Funds Owned</h2>
            <div class="panel-history">
                <div class="table" id="cusViewAcctTable" >
                <table style="overflow-x:auto; overflow-y:auto; max-height:900px">
                <tbody>
                    <tr>
                        <th><b> Fund Name </b></th>
                        <th><b> Fund Symbol </b></th>
                        <th><b> Number of Shares </b></th>
                        <th><b>Total Value ($)</b></th>
                    </tr>
                    
				<c:forEach var="fund" items="${fundsList}">
				<tr>
					<c:forEach var="info" items="${fund}">
						<td>${info}</td>
					</c:forEach>
				</tr>
				</c:forEach>
				</tbody>
                </table>
                </div> 
            </div>
            
        </div>
       </main>
    </div>
<jsp:include page="footer.jsp" />