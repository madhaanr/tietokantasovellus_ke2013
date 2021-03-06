<%-- 
    Document   : kirjaatunteja
    Created on : 24-Aug-2013, 15:46:48
    Author     : mhaanran
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css">
        <title>Projektin työaikaseuranta</title>
    </head>
    <body>
        <h1>Projektiin ${projektinNimi} tehtyjen työtuntien kirjaaminen</h1>
        <h2>Olet kirjautuneena sisään käyttäjänä: ${knimi}</h2>
        <form action="${pageContext.request.contextPath}/KirjauduUlos" method="post">
            <input type="submit" value="Kirjaudu ulos" />
        </form>
        <h3>Käyttäjän ${knimi} kirjaukset projektiin ${projektinNimi}</h3>
        <table>
            <th>Työtehtävä</th><th>Tunnit</th><th>Päivämäärä</th><th>Selitys</th>
        <c:forEach var="kirjaus" items="${kirjaukset}">
            <tr>
                <td>${kirjaus.tyotehtavanNimi}</td> 
                <td>${kirjaus.tehdytTunnit}</td> 
                <td><fmt:formatDate value="${kirjaus.paivamaara}" pattern="ddMMyyyy" /></td>
                <td>${kirjaus.selitys}</td>
            </tr> 
        </c:forEach>        
            <tr>         
                <td>Tehtyjen tuntien summa</td><td id="summa">${tehtyjenTuntienSumma}</td>
            </tr>
        </table>
        <h3>Kirjaa työtunteja</h3>
        <h4>Syötä päivämäärä kahdeksan merkin jonona muodossa "ddMMyyyy".</h4>
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
        </form>
        <h3>Poista kirjaus</h3>
        <form name="poista_kirjaus" 
              action="${pageContext.request.contextPath}/PoistaKirjaus?name=${projektinNimi}"
              method="post">
            <input type="hidden" name="projektin_nimi" /> 
            <select name="tyotehtavanNimi">
                <c:forEach var="kirjaus" items="${kirjaukset}" >
                    <option><c:out value="${kirjaus.tyotehtavanNimi}"/></option>
                </c:forEach>
            </select>
            Päivämäärä <input type="text" name ="paivamaara" />
            <br><input type="submit" value="Poista kirjaus" />    
        </form>
        <a href="${pageContext.request.contextPath}/Projektit">Palaa projektien hallinta sivulle</a>
    </body>
</html>
