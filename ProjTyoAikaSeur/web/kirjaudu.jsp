<%-- 
    Document   : kirjaudu
    Created on : 08-Aug-2013, 13:03:52
    Author     : mhaanran
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css">
        <title>Projektin työaikaseuranta</title>
    </head>
    <body>
        <h1>Kirjaudu projektin työaikaseuranta järjestelmään!</h1>
        <table>
            <form name="kirjautuminen" 
                  action="${pageContext.request.contextPath}/Kirjaudu"
                  method="post">
                <tr>
                    <td>Käyttäjätunnus:</td> 
                    <td><input type="text" name="kayttajatunnus" id="formfield"/></td>
                </tr>
                <tr>
                    <td>Salasana:</td> 
                    <td><input type="password" name="salasana" id="formfield"/> </td>
                </tr>
                <tr> 
                    <td><input type="submit" value="Kirjaudu" />  </td>
                </tr>
            </form>
        </table>
        <h2>${virhe}</h2>
    </body>
</html>
