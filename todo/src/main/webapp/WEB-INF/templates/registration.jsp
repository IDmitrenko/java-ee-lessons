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
            <c:url value="${requestScope.action}" var="namePostUrl"/>
            <form action="${namePostUrl}" method="post">

                <div class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${name}" placeholder="Enter name">
                </div>

                <div class="form-group">
                    <label>Address</label>
                    <input type="text" class="form-control" id="address" name="address"
                           value="${address}">
                </div>

                <div class="form-group">
                    <label>Phone</label>
                    <input type="tel" pattern="+[0-9]{1}([0-9]{3})[0-9]{3}-[0-9]{2}-[0-9]{2}" class="form-control" id="phone" name="phone"
                           value="${phone}">
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="footer_scripts.jsp" %>

</body>