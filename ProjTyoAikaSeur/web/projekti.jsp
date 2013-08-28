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
        <link href="style.css" rel="stylesheet" type="text/css">
        <title>Projektin työaikaseuranta</title>
    </head>
    <body>
        <h1>Lisää työtehtäviä projektiin: ${projektinNimi}</h1>
        <h2>Olet kirjautunut sisään käyttäjänä ${knimi}</h2>
        <form action="${pageContext.request.contextPath}/KirjauduUlos" method="post">
            <input type="submit" value="Kirjaudu ulos" />
        </form>
        <c:if test="${rooli}">
        <h2>Lisää työtehtävä</h2>
        <form name="projektin_lisaaminen" 
                  action="${pageContext.request.contextPath}/LisaaTyotehtava?name=${projektinNimi}"
                  method="post">
                Työtehtävän nimi <input type="text" name="tyotehtavanNimi" /> 
                Budjetoidut työtunnit <input type="text" name="budjetoidutTyotunnit" />
                <br><input type="submit" value="Lisää työtehtävä" />
                <h2>${viesti}</h2>
        </form>
        <h3>Poista työtehtävä</h3>
            <form action="${pageContext.request.contextPath}/PoistaTyotehtava?name=${projektinNimi}" method="post">
                <input type="text" name="tyotehtavan_nimi" /> <br>
                <input type="submit" value="Poista työtehtävä" />
            </form>
        </c:if>
        <h3>Projektin ${projektinNimi} työtehtävät</h3>
        <c:forEach var="tyotehtava" items="${tyotehtavat}">    
                ${tyotehtava.tyotehtavanNimi} ${tyotehtava.budjetoidutTyotunnit}<br>      
        </c:forEach>
        <c:if test="${rooli}">
        <h3>Lisää työntekijä projektiin</h3>
        <form name="tyontekijan_lisaaminen_projektiin" action="${pageContext.request.contextPath}/LisaaTyontekijaProjektiin"
              method="post">
            <input type="text" name="tyontekijanNimi"/> <br>
            <input type="hidden" name="projektinNimi" value="${projektinNimi}"/> <br>
            <input type="submit" value="Lisää työntekijä projektiin"/>
        </form>
        <h3>Kaikki työntekijät</h3>
        <c:forEach var="kayttaja" items="${kayttajat}">    
                ${kayttaja.nimi} Kayttajatunnus: ${kayttaja.kayttajatunnus} Rooli: ${kayttaja.rooli}<br>      
        </c:forEach>
        </c:if>
        
        <h3>Projektin ${projektinNimi} työntekijät</h3>
        <c:forEach var="tyontekija" items="${tyontekijat}">    
                ${tyontekija} <br>      
        </c:forEach>
        <h3>Kirjaa tunteja</h3>
        
        <a href="${pageContext.request.contextPath}/Projektit">Palaa projektien hallinta sivulle</a>
    </body>
</html>
