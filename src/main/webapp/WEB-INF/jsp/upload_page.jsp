<%--
  ~ Copyright (c) 2018 Sergei Visotsky
  --%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta content="text/html" charset="UTF-8">
    <title>docx uploader</title>
</head>
<body>

<p><a href="<c:url value="/download"/>">Download template</a></p>
<spring:url value="/upload" var="uploadUrl"/>
<form:form method="post" modelAttribute="formUpload" action="${uploadUrl}" enctype="multipart/form-data">
    <p>Select docx file to upload:</p>
    <p>
        <form:input path="file" type="file"/>
        <form:errors path="file" cssStyle="color: red"/>
    </p>
    <p>
        <button type="submit">Upload</button>
    </p>
</form:form>

</body>
</html>