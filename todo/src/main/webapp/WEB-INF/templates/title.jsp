<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp" %>

<body>

<%@ include file="header.jsp" %>

<form>
    <div class="form-group">
        <label for="textAreaCompany"> О компании </label>
        <textarea class="form-control" id="textAreaCompany" rows="15">
            Наша компания приветствует Вас. Мы делаем всё и много. Наши табуретки
            пользуются большим спросом и имееют низкую стоимость.
            Наша команда - это грамотные и отзывчивые люди.
            Купите у нас десять табуреток по цене пятнадцати и одиннадцатая
            Вам достанется бесплатно.
        </textarea>
    </div>
</form>

<%@ include file="footer_scripts.jsp" %>

</body>