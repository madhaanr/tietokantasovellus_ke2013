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
        <link href="style.css" rel="stylesheet" type="text/css">
        <title>Projektin työaikaseuranta</title>
    </head>
    <body>
        <h1>Projektien hallinta</h1>
        <h2>Olet kirjautuneena sisään käyttäjänä: ${knimi}</h2>
        <form action="${pageContext.request.contextPath}/KirjauduUlos" method="post">
            <input type="submit" value="Kirjaudu ulos" />
        </form>
        <c:if test="${rooli}">
            <h3>Raportit</h3>
            <a href="${pageContext.request.contextPath}/Raportit">Raportit</a>

            <h3>Lisää projekti</h3>
            <table>
                <form name="projektin_lisaaminen" 
                      action="${pageContext.request.contextPath}/Projektit"
                      method="post">           
                    <tr>
                        <td>Projektin nimi:</td> 
                        <td><input type="text" name="projektin_nimi" id="formfield"/></td>
                    </tr>
                    <tr>
                        <td>Työtunti budjetti:</td> 
                        <td><input type="text" name="tyoTuntiBudjetti" id="formfield"/></td>
                    </tr>
                    <tr>
                        <td>Alkamispäivämäärä:</td> 
                        <td><input type="text" name ="alkamisPaivaMaara" id="formfield"/></td> 
                    </tr>
                    <tr>
                        <td>Loppumispäivämäärä:</td> 
                        <td><input type="text" name ="loppumisPaivaMaara" id="formfield"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Lisää projekti" /></td>
                    </tr>                      
                </form>         
            </table>
            <h2>${virhe}</h2>
            <h3>Projektit</h3>
            <table>
                <th>Projektin nimi</th><th>Työtuntibudjetti</th><th>Alkamispäivämäärä</th><th>Loppumispäivämäärä</th>
                <form name="projektin_tietojen_muokkaus" 
                      action="${pageContext.request.contextPath}/ProjektinMuokkaus"
                      method="post">
                    <c:forEach var="projekti" items="${projektit}">  
                        <tr>
                            <td id="projektitlista"><a href="${pageContext.request.contextPath}/LisaaTyotehtava?name=${projekti.projektinNimi}" 
                                                       id="${projekti.projektinNimi}">${projekti.projektinNimi}</a></td> 
                            <td id="projektitlista"><input type="text" name="tyoTuntiBudjetti" value="${projekti.budjetoidutTyotunnit}"/></td>  
                            <td id="projektitlista"><input type="text" name="alkamisPaivaMaara" value="<fmt:formatDate value="${projekti.alkamisPaivaMaara}" pattern="ddMMyyyy"/>"/></td>
                            <td id="projektitlista"><input type="text" name="loppumisPaivaMaara" value="<fmt:formatDate value="${projekti.loppumisPaivaMaara}" pattern="ddMMyyyy"/>"/></td>
                            <input type="hidden" name="projektin_nimi" value="${projekti.projektinNimi}">
                            <td><input type="submit" value="Muokkaa projektia"/></td>
                        </tr>
                    </c:forEach>
                </form>
            </table>
            <h3>Poista projekti</h3>
            <form action="${pageContext.request.contextPath}/PoistaProjekti" method="post">
                Valitse poistettava projekti:
                <select name="projektin_nimi">
                    <c:forEach var="projekti" items="${projektit}">
                        <option> <c:out value="${projekti.projektinNimi}"/> </option>
                    </c:forEach>          
                </select>
                <input type="submit" value="Poista projekti" />
            </form>

            <h3>Käyttäjät</h3>
            <c:forEach var="kayttaja" items="${kayttajat}">    
                ${kayttaja.nimi} Kayttajatunnus: ${kayttaja.kayttajatunnus} Rooli: ${kayttaja.rooli}<br>      
            </c:forEach>
        </c:if> 

        <c:if test="${!rooli}">
            <h3>Kayttajan ${knimi} projektit: </h3>
            <table>
                <c:forEach var="kayttajanProjekti" items="${kayttajanProjektit}">    
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/KirjaaTunteja?name=${kayttajanProjekti}">${kayttajanProjekti}</a></td>
                    </tr>    
                </c:forEach>
            </table>
            <h3>Raportit</h3>
            <a href="${pageContext.request.contextPath}/Raportit">Raportit</a>
        </c:if>
    </body>
</html>
