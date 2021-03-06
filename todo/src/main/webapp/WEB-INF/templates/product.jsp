<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp" %>

<body>

<%@ include file="header.jsp" %>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="${requestScope.action}" var="todoProductUrl"/>
            <form action="${todoProductUrl}">

                <div class="form-group">
                    <label>Description</label>
                    <input type="text" class="form-control" id="description" name="description"
                           value="${todo.description}" placeholder="Enter description">
                </div>

                <div class="form-group">
                    <label>Target date</label>
                    <input type="date" class="form-control" id="targetDate" name="targetDate"
                           value="${todo.targetDate}">
                </div>

                <div class="form-group">
                    <label for="textAreaProduct"> О продукте ${todo.description}</label>
                    <textarea class="form-control" id="textAreaProduct" rows="10">
                        Кролики это не только ценный мех, но и три-четыре килограмма сала.
                    </textarea>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="footer_scripts.jsp" %>

</body>