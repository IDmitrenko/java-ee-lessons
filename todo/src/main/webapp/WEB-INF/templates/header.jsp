<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <c:url value="/todos" var="homeUrl"/>
    <a class="navbar-brand" href="${homeUrl}">ToDo</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">

                <a class="nav-link" href="${homeUrl}">List</a>
            </li>
        </ul>
    </div>
</nav>