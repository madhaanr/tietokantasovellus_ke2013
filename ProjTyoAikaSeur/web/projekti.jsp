<%-- 
    Document   : projekti
    Created on : 22-Aug-2013, 14:23:02
    Author     : mhaanran
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projektin työaikaseuranta</title>
    </head>
    <body>
        <h1>Lisää työtehtäviä ja työntekijöitä projektiin ${projektinNimi}</h1>
        <h2>Lisää työtehtävä</h2>
        <form name="projektin_lisaaminen" 
                  action="${pageContext.request.contextPath}/LisaaTyotehtava?name=${projektinNimi}"
                  method="post">
                Työtehtävän nimi <input type="text" name="tyotehtavanNimi" /> 
                Budjetoidut työtunnit <input type="text" name="budjetoidutTyotunnit" />
                <br><input type="submit" value="Lisää työtehtävä" />
                <h2>${viesti}</h2>
        </form>
        <h3>Projektin ${projektinNimi} työtehtävät</h3>
        <c:forEach var="tyotehtava" items="${tyotehtavat}">    
                ${tyotehtava.tyotehtavanNimi} ${tyotehtava.budjetoidutTyotunnit}<br>      
        </c:forEach>
        <h3>Poista tyotehtava</h3>
            <form action="${pageContext.request.contextPath}/PoistaTyotehtava?name=${projektinNimi}" method="post">
                <input type="text" name="tyotehtavan_nimi" /> <br>
                <input type="submit" value="Poista tyotehtava" />
            </form>
        <h3>Kayttajat</h3>
        <c:forEach var="kayttaja" items="${kayttajat}">    
                ${kayttaja.nimi} Kayttajatunnus: ${kayttaja.kayttajatunnus} Rooli: ${kayttaja.rooli}<br>      
        </c:forEach>
        
        <a href="${pageContext.request.contextPath}/Projektit">Palaa projektit sivulle</a>
    </body>
</html>
