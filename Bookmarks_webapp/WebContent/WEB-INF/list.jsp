<jsp:include page="template-top.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title>FavoriteList </title>
    </head>
    
    <body>
    
        <h2>Favorite List</h2>
        <jsp:include page="error-list.jsp" />
		
<%@ page import="edu.cmu.cs.webapp.hw4.databean.FavoriteBean" %>
<p>
<p style="font-size: x-large">The list has ${ fn:length(favoriteList) } items.</p>
	<table>
<c:set var="count" value="0" />
 <c:forEach var="f" items="${(favoriteList)}" >
<c:set var="count" value="${ count+1 }" />
        
        <tr>
            
             <td style="font-size: x-large"> &nbsp; ${ count }. &nbsp;</td> 
             
             <td>
            <form name="f${f.fid }"  action="update.do" method="POST">
            <input type="hidden" name="hyperlink" value="${f.url}"/>
            <input type="hidden" name="fid" value="${f.fid}"/>
            <a href="#" target = "_blank" onclick ="document.f${f.fid}.submit(); return false;">${f.url}</a>
            </td>
            </tr>       
             
             <tr>
             <td>Comment: ${f.comment}</td>
             </tr>
             
             <tr>
             <td>ClickCount: ${f.clickCount}</td>
             </tr>   
           
            
</form>
 
               
</c:forEach>
	</table>
</p>

<jsp:include page="template-bottom.jsp" />
