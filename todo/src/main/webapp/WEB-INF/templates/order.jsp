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
            <c:url value="${requestScope.action}" var="todoOrderUrl"/>
            <h5 class="text-success text-center"> Ваш заказ </h5>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Description</th>
                    <th scope="col">Target date</th>
                    <th scope="col">Count</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${requestScope.order}">
                    <tr>
                        <th scope="row">
                            <c:out value="${order.key.id}"/>
                        </th>
                        <td>
                            <c:out value="${order.key.description}"/>
                        </td>
                        <td>
                            <c:out value="${order.key.targetDate}"/>
                        </td>
                        <td>
                            <c:out value="${order.value}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-12">
            <c:url value="/todos/registration" var="todoRegistrationUrl"/>
            <a style="float: right" class="btn btn-primary" href="${todoRegistrationUrl}"> Оформить заказ </a>
        </div>
    </div>
</div>

<%@ include file="footer_scripts.jsp" %>

</body>