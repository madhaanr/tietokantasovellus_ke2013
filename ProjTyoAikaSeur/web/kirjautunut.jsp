<%-- 
    Document   : kirjautunut
    Created on : 12-Aug-2013, 12:12:32
    Author     : mhaanran
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2>Tervetuloa ${ktunnus}</h2>
        <h3>Lisää projekti</h3>
         <form name="projektin_lisaaminen" 
              action="${pageContext.request.contextPath}/Projektit"
              method="post">
            Projektin nimi: <input type="text" name="projektin_nimi" /> <br>
            <input type="submit" value="Lisää projekti" />
            
            <h2>${viesti}</h2>
        </form>
        <c:forEach var="projekti" items="${projektit}">
            ${projekti.projektinNimi} <br>
        </c:forEach>
    </body>
</html>