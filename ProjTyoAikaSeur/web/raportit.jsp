<%-- 
    Document   : raportit
    Created on : 27-Aug-2013, 14:44:39
    Author     : mhaanran
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Raportointi</title>
    </head>
    <body>
        <h1>Raportit</h1>
        <c:if test="${rooli}">
            <h2>Projektipäällikön raportit</h2>
            <h3>Raportti viikon aikana tehdyistä tunneista</h3>
             <form name="viikko_raportti" 
              action="${pageContext.request.contextPath}/Raportit"
              method="post">
            Raportin alkamispäivä <input type="text" name="alkamisPaiva" />
            Raportin loppumispäivä <input type="text" name ="loppumisPaiva" />
            <br><input type="submit" value="Tulosta raportti" />           
        </form>
            
            <c:forEach var="viikkor" items="${viikkoraportti}">
                ${viikkor.kayttajatunnus} ${viikkor.projektinNimi} ${viikkor.tyotehtavanNimi} ${viikkor.tehdytTunnit} <fmt:formatDate value="${viikkor.paivamaara}" pattern="dd-MM-yyyy"/> ${viikkor.selitys} <br>
            </c:forEach>
            <h3>Yksityiskohtainen raportti</h3>
        </c:if>
        <c:if test="${!rooli}">
            <h2>Työntekijän raportit</h2>
        </c:if>
        <a href="${pageContext.request.contextPath}/Projektit">Palaa projektit sivulle</a>
    </body>
</html>
