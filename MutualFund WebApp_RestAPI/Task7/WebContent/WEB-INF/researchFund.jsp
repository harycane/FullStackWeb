<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
    <head>
        <title>Research Fund</title>
        <jsp:include page="css.jsp" />
    </head>   
    <body>
      <div id = "wrapper">
        <jsp:include page="header.jsp" />
        <jsp:include page="customerNav.jsp" />
    <main>
        <div class="panel">
            <h2> Research Fund</h2>
            <div class="panel-profile">
                <div class="operation" id="researchFundByName">
                  <ul>
                  <c:forEach var = "fund" items = "${hlist}">
		                <li class="formBtn" id ="fundName"><a data-toggle="collapse" data-parent="#accordion" href="#collapse${fund.fundid}">${fund.name}</a></li>
				  </c:forEach>
				</ul>
                </div>

                <div class="content">
			      <c:forEach var = "fund" items = "${hlist}">
				      <div id="collapse${fund.fundid}" class="panel-collapse collapse">
				        <div class="panel-body" id="researchFundPanel">
				          <div id="container${fund.fundid}" style="width:85%; min-height:300px;"></div>
				           <div class="panel-history" id="researchFundHis">
			                <div class="table" >
			                <table style="overflow-x:auto ; overflow-y:auto; max-height:500px">
				                <tbody>
					              <tr>
					                <th>Price Date</th>
					                <th>Price</th>
					              </tr>
					              <c:forEach var = "ph" items = "${fund.history}">
					                <tr>
					                  <td><fmt:formatDate pattern="MM/dd/yyyy" value="${ph.pricedate}"/></td>
					                  <td><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${ph.price}"/></td>
					                </tr>
					              </c:forEach>
					            </tbody>
				            </table>
				          </div>
				         </div>
			            </div>
			          </div>
			        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
			        <script src="http://code.highcharts.com/highcharts.js"></script>
			        <script>
						$(function () { 
						    var myChart = Highcharts.chart('container${fund.fundid}', {
						        chart: {
						            type: 'line'
						        },
						        title: {
						            text: 'Fund Price History: ${fund.name}'
						        },
						        xAxis: {
						            categories: [
						            	<c:forEach var = "hd" items = "${fund.history}">
						            	  '${hd.pricedate}',
						            	</c:forEach> ]
						        },
						        yAxis: {
						            title: {
						                text: 'Price'
						            }
						        },
						        series: [{
						            name: '${fund.name}',
						            data: [
						            	<c:forEach var = "hp" items = "${fund.history}">
						            	  <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" groupingUsed="FALSE" value="${hp.price}"/>,
						            	</c:forEach>]
						        }]
						    });
						});
				    </script>
				    <br>
			      </c:forEach>
			      </div>
			    </div>
			  </div>
	  </main>
<jsp:include page="footer.jsp" />
</body>
</html>