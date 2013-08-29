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
        <link href="style.css" rel="stylesheet" type="text/css">
        <title>Projektin työaikaseuranta</title>
    </head>
    <body>
        <h1>Raportit</h1>
        <h2>Olet kirjautuneena sisään käyttäjänä: ${knimi}</h2>
        <form action="${pageContext.request.contextPath}/KirjauduUlos" method="post">
            <input type="submit" value="Kirjaudu ulos" />
        </form>
        <c:if test="${rooli}">
            <h2>Projektipäällikön raportit</h2>
            <h3>Kausiraportti</h3>
            <form name="viikko_raportti" 
                  action="${pageContext.request.contextPath}/Raportit"
                  method="post">
                Raportin alkamispäivä <input type="text" name="alkamisPaiva" />
                Raportin loppumispäivä <input type="text" name ="loppumisPaiva" />
                <br><input type="submit" value="Tulosta raportti" />  
            </form>   
            <table>
                <th>Projekti</th><th>Työtehtävä</th><th>Käyttäjätunnus</th>
                <th>Tehdyt työtunnit</th><th>Päivämäärä</th><th>Selitys</th>
                <c:forEach var="viikkor" items="${viikkoraportti}">
                    <tr>
                        <td class="viikkoraportti">${viikkor.projektinNimi}</td> 
                        <td class="viikkoraportti">${viikkor.tyotehtavanNimi}</td>  
                        <td class="viikkoraportti">${viikkor.kayttajatunnus}</td>                 
                        <td class="viikkoraportti">${viikkor.tehdytTunnit}</td> 
                        <td class="viikkoraportti"><fmt:formatDate value="${viikkor.paivamaara}" pattern="dd-MM-yyyy"/></td>
                        <td class="viikkoraporttiselitys">${viikkor.selitys}</td>
                    </tr>
                </c:forEach>
                <tr>         
                    <td>Tehtyjen tuntien summa</td><td id="summa">${tehtyjenTuntienSumma}</td>
                </tr>
            </table>
        </c:if>
        <c:if test="${!rooli}">
            <h2>Työntekijän raportit</h2>
            <h3>Kausiraportti</h3>
            <form name="viikko_raportti" 
                  action="${pageContext.request.contextPath}/Raportit"
                  method="post">
                Raportin alkamispäivä <input type="text" name="alkamisPaiva" />
                Raportin loppumispäivä <input type="text" name ="loppumisPaiva" />
                <br><input type="submit" value="Tulosta raportti" />  
            </form>   
            <table>
                <th>Projekti</th><th>Työtehtävä</th><th>Käyttäjätunnus</th>
                <th>Tehdyt työtunnit</th><th>Päivämäärä</th><th>Selitys</th>
                <c:forEach var="viikkor" items="${viikkoraportti}">
                    <tr>
                        <td class="viikkoraportti">${viikkor.projektinNimi}</td> 
                        <td class="viikkoraportti">${viikkor.tyotehtavanNimi}</td>  
                        <td class="viikkoraportti">${viikkor.kayttajatunnus}</td>                 
                        <td class="viikkoraportti">${viikkor.tehdytTunnit}</td> 
                        <td class="viikkoraportti"><fmt:formatDate value="${viikkor.paivamaara}" pattern="dd-MM-yyyy"/></td>
                        <td class="viikkoraporttiselitys">${viikkor.selitys}</td>
                    </tr>
                </c:forEach>
                <tr>         
                    <td>Tehtyjen tuntien summa</td><td id="summa">${tehtyjenTuntienSumma}</td>
                </tr>
            </table>
        </c:if>
        <a href="${pageContext.request.contextPath}/Projektit">Palaa projektien hallinta sivulle</a>
    </body>
</html>
