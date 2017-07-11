<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Add a new Favorite:
</p>

<jsp:include page="error-list.jsp" />


	
<p>
	
	<form method="post" action="addfav.do">
		<table>
			<tr>
				<td>URL: </td>
				<td colspan="2"><input type="url" name="url" placeholder = "http://www." value="${url}" /></td>
			</tr>
			<tr>
				<td>Comment: </td>
				<td><input type="text" name="comment" value="${comment}"/></td>
				
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input type="submit" name="button" value="AddFavorite"/>
				</td>
			</tr>
		</table>
	</form>
</p>
<hr/>
<%@ page import="edu.cmu.cs.webapp.hw4.databean.FavoriteBean" %>

<p>
<p style="font-size: x-large">The list now has ${ fn:length(favoriteList) } items.</p>
	<table>
<c:set var="count" value="0" />
 <c:forEach var="f" items="${(favoriteList)}" >
<c:set var="count" value="${ count+1 }" />
        <tr>
            <td>
                <form method="POST" action="remove.do">
                    <input type="hidden" name="id" value="${f.fid}"/>
                    <input type="submit" value="X"/>
                </form>
            </td>
            <td style="font-size: x-large"> &nbsp; ${ count }. &nbsp; </td>
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
