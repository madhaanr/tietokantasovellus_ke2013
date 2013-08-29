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
        <h2>Olet kirjautuneena sisään käyttäjänä: ${knimi}</h2>
        <form action="${pageContext.request.contextPath}/KirjauduUlos" method="post">
            <input type="submit" value="Kirjaudu ulos" />
        </form>
        <c:if test="${rooli}">
            <h2>Lisää työtehtävä</h2>
            <table>
                <form name="projektin_lisaaminen" 
                      action="${pageContext.request.contextPath}/LisaaTyotehtava?name=${projektinNimi}"
                      method="post">
                    <tr>
                        <td>Työtehtävän nimi:</td> 
                        <td><input type="text" name="tyotehtavanNimi" /></td>
                        <td>Budjetoidut työtunnit:</td> 
                        <td><input type="text" name="budjetoidutTyotunnit" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Lisää työtehtävä" /></td>
                    </tr>    
                </form>
                <h2>${virhe}</h2>
            </table>

        </c:if>

        <h3>Projektin ${projektinNimi} työtehtävät ja työtuntibudjetti ${projTyotuntibudjetti}</h3>
        <table>
            <c:forEach var="tyotehtava" items="${tyotehtavat}">    
                <tr>
                    <td>${tyotehtava.tyotehtavanNimi}</td> 
                    <td>${tyotehtava.budjetoidutTyotunnit}</td>
                </tr> 
            </c:forEach>
        </table>
        <table id="summa">
                <tr>
                    <td>Tuntien summa</td>
                    <td>${tyotehtavienTuntienSumma}</td>
                </tr>
        </table>
        <c:if test="${rooli}">
            <h3>Poista työtehtävä</h3>
            <form action="${pageContext.request.contextPath}/PoistaTyotehtava?name=${projektinNimi}" method="post">
                Valitse poistettava tyotehtava:
                <select name="tyotehtavan_nimi">
                    <c:forEach var="tyotehtava" items="${tyotehtavat}">
                        <option> <c:out value="${tyotehtava.tyotehtavanNimi}"/> </option>
                    </c:forEach>          
                </select>
                <input type="submit" value="Poista työtehtävä" />
            </form>
            <h3>Kaikki työntekijät</h3>
            <table>
                <th>Käyttäjän nimi</th><th>Käyttäjätunnus</th><th>Rooli</th>
                    <c:forEach var="kayttaja" items="${kayttajat}">    
                    <tr>
                        <td>${kayttaja.nimi}</td> <td>${kayttaja.kayttajatunnus}</td> <td>${kayttaja.rooli}</td>
                    </tr>     
                </c:forEach>
            </table>
            <h3>Lisää työntekijä projektiin</h3>
            <form name="tyontekijan_lisaaminen_projektiin" action="${pageContext.request.contextPath}/LisaaTyontekijaProjektiin?name=${projektinNimi}" method="post">
                <select name="tyontekijanNimi">
                    <c:forEach var="kayttaja" items="${kayttajat}">
                        <option> <c:out value="${kayttaja.kayttajatunnus}"/> </option>
                    </c:forEach>          
                </select>
                <input type="hidden" name="tyontekijanNimi" value="${kayttaja.kayttajatunnus}" />
                <input type="hidden" name="projektinNimi" value="${projektinNimi}" />
                <input type="submit" value="Lisää työntekijä projektiin"/>
            </form>

            <h3>Projektin ${projektinNimi} työntekijät</h3>
            <c:forEach var="tyontekija" items="${tyontekijat}">    
                ${tyontekija} <br>      
            </c:forEach>

            <h3>Poista työntekijä projektista</h3>
            <form name="tyontekijan_poistaminen_projektista" action="${pageContext.request.contextPath}/PoistaTyontekijaProjektista?name=${projektinNimi}" method="post">
                <select name="tyontekijanNimi">
                    <c:forEach var="kayttaja" items="${kayttajat}">
                        <option> <c:out value="${kayttaja.kayttajatunnus}"/> </option>
                    </c:forEach>          
                </select>
                <input type="hidden" name="tyontekijanNimi" value="${kayttaja.kayttajatunnus}" />
                <input type="hidden" name="projektinNimi" value="${projektinNimi}" />
                <input type="submit" value="Poista työntekijä projektista"/>
            </form>

        </c:if>


        <a href="${pageContext.request.contextPath}/Projektit">Palaa projektien hallinta sivulle</a>
    </body>
</html>
