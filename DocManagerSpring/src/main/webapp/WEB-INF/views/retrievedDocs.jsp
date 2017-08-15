<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />



<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet" />







<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Document Manager</title>
</head>
<body>

	
	<br />
	<br />
	<div class="container">

		<c:if test="${not empty docList}">
		<h2>Document List</h2>
		
		<c:forEach var="docVar" items="${docList}">

			<div class="col-md-4">
				<div class="thumbnail">
					<a href="${imageDir}${docVar}"> <img src="${imageDir}${docVar}"
						style="width: 100%">
					<div class="caption">
							<p>${docVar}</p>
						</div>
					</a>
				</div>



			</div>

		</c:forEach>
		</c:if>
		
		


	</div>

	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>