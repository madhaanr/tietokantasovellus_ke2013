<%-- 
    Document   : kirjaudu
    Created on : 08-Aug-2013, 13:03:52
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
        <h1>Kirjaudu projektin työaikaseuranta järjestelmään!</h1>
        
        <form name="kirjautuminen" 
              action="${pageContext.request.contextPath}/Kirjaudu"
              method="post">
            Käyttäjätunnus: <input type="text" name="kayttajatunnus" /> <br>
            Salasana: <input type="password" name="salasana" /> <br>
            <input type="submit" value="Kirjaudu" />          
            <h2>${viesti}</h2>
        </form>
    </body>
</html>
