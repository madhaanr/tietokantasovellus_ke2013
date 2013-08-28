<%-- 
    Document   : kirjaatunteja
    Created on : 24-Aug-2013, 15:46:48
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
        <h1>Projektin ${projektinNimi} työtuntien kirjaaminen</h1>
        <h2>Olet kirjautunut sisään käyttäjänä ${knimi}</h2>
        <form action="${pageContext.request.contextPath}/KirjauduUlos" method="post">
            <input type="submit" value="Kirjaudu ulos" />
        </form>
        <h3>Käyttäjän ${knimi} kirjaukset projektiin ${projektinNimi}</h3>
        <table>
            <th>Työtehtävä</th><th>Tunnit</th><th>Päivämäärä</th>
        <c:forEach var="kirjaus" items="${kirjaukset}">
            <tr>
                <td>${kirjaus.tyotehtavanNimi}</td> 
                <td>${kirjaus.tehdytTunnit}</td> 
                <td><fmt:formatDate value="${kirjaus.paivamaara}" pattern="dd-MM-yyyy" /></td>
                <td>${kirjaus.selitys}</td>
            </tr> 
        </c:forEach>
        </table>
        <h3>Kirjaa työtunteja</h3>
        <form name="tuntien_kirjaaminen" 
              action="${pageContext.request.contextPath}/KirjaaTunteja?name=${projektinNimi}"
              method="post">
            <input type="hidden" name="projektin_nimi" /> 
            <select name="tyotehtavanNimi">
                <c:forEach var="tyotehtava" items="${tyotehtavat}" >
                    <option><c:out value="${tyotehtava}"/></option>
                </c:forEach>
            </select>
            Tehdyt työtunnit <input type="text" name="tehdytTunnit" />
            Päivämäärä <input type="text" name ="paivamaara" />
            Selitys <input type="text" name="selitys"/>
            <br><input type="submit" value="Kirjaa työtunnit" />

            <h2>${viesti}</h2>

        </form>
        <a href="${pageContext.request.contextPath}/Projektit">Palaa projektien hallinta sivulle</a>
    </body>
</html>
