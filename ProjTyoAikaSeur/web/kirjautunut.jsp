<%-- 
    Document   : kirjautunut
    Created on : 12-Aug-2013, 12:12:32
    Author     : mhaanran
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Projektit</h1>
        <h2>Tervetuloa ${knimi}</h2>
        <form action="${pageContext.request.contextPath}/KirjauduUlos" method="post">
            <input type="submit" value="Kirjaudu ulos" />
        </form>
        <c:if test="${rooli}">
            <h3>Raportit</h3>
            <a href="${pageContext.request.contextPath}/Raportit">Raportit</a>
               
            <h3>Lisää projekti</h3>
            <form name="projektin_lisaaminen" 
                  action="${pageContext.request.contextPath}/Projektit"
                  method="post">
                Projektin nimi: <input type="text" name="projektin_nimi" /> 
                Työtunti budjetti: <input type="text" name="tyoTuntiBudjetti" />
                Alkamispäivämäärä: <input type="text" name ="alkamisPaivaMaara" />
                Loppumispäivämäärä: <input type="text" name ="loppumisPaivaMaara" />
                <br><input type="submit" value="Lisää projekti" />

                <h2>${viesti}</h2>
            </form>
            <h3>Poista projekti</h3>
            <form action="${pageContext.request.contextPath}/PoistaProjekti" method="post">
                <input type="text" name="projektin_nimi" /> <br>
                <input type="submit" value="Poista projekti" />
            </form>

            <h3>Projektit</h3>
            <c:forEach var="projekti" items="${projektit}">
                <a href="${pageContext.request.contextPath}/LisaaTyotehtava?name=${projekti.projektinNimi}" id="${projekti.projektinNimi}">${projekti.projektinNimi}</a> Tyotuntibudjetti: ${projekti.budjetoidutTyotunnit} Alkamispäivämäärä: <fmt:formatDate value="${projekti.alkamisPaivaMaara}" pattern="dd-MM-yyyy"/> Loppumispäivämäärä: <fmt:formatDate value="${projekti.loppumisPaivaMaara}" pattern="dd-MM-yyyy"/> <br>
            </c:forEach>

            <h3>Kayttajat</h3>
            <c:forEach var="kayttaja" items="${kayttajat}">    
                ${kayttaja.nimi} Kayttajatunnus: ${kayttaja.kayttajatunnus} Rooli: ${kayttaja.rooli}<br>      
            </c:forEach>
        </c:if> 

        <c:if test="${!rooli}">
            <h3>Kayttajan ${knimi} projektit: </h3>
            <c:forEach var="kayttajanProjekti" items="${kayttajanProjektit}">    
                <a href="${pageContext.request.contextPath}/KirjaaTunteja?name=${kayttajanProjekti}">${kayttajanProjekti}</a><br>      
            </c:forEach>
        </c:if>
    </body>
</html>
