<%-- 
    Document   : index
    Created on : May 16, 2025, 8:42:32 AM
    Author     : khoac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <h1>
            HTML Login Page
        </h1>
        <form action="#" method="post">
            Textbox <input type="text" name="txtText" value="" size="5" /><br/>
            Password <input type="password" name="txtPassword" value="" /><br/>
            Hidden <input type="hidden" name="txtHidden" value="" /><br/>
            Male <input type="checkbox" name="chkCheck" value="ON" checked="checked" /><br/>
            Status
            <input type="radio" name="rdoStatus" value="Single" checked="checked" />Single<br/>
            <input type="radio" name="rdoStatus" value="Married" />Married<br/>
            <input type="radio" name="rdoStatus" value="Divorsed" />Divorsed<br/>
            ComboBox <select name="txtCombo">
                <option value="Serviet">JSP and Serviet</option>
                <option value="EJB">EJB</option>
            </select><br/>
            Multiple <select name="txtList" multiple="multiple" size="2">
                <option value="Serviet" selected>JSP and Serviet</option>
                <option value="EJB" selected>EJB</option>
                <option value="Java">Core Java</option>
            </select><br/>
            TextArea <textarea name="txtArea" rows="4" cols="20">
This is a form parameters demo!!!!
            </textarea><br/>
            <input type="submit" name="txtB" />
            <input type="submit" value="Register" name="action" />
            <input type="reset" name="txtB" />
            <input type="button" value="JavaScript" name="txtB" onclick="" />
        </form>
    </body>
</html>
