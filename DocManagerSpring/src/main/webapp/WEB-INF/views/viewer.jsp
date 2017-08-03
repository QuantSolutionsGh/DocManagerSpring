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


	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Document Manager</a>
			</div>
			<ul class="nav navbar-nav">

				<li><a href="index">Document Upload</a></li>
				<li class="active"><a href="viewer">Document Viewer</a></li>

			</ul>
		</div>
	</nav>


	<br />

	<br />
	<div class="container">

		<form class="form-horizontal" id="search-form"
			style="margin-top: 70px">
			<div class="form-group">
				<label class="control-label col-sm-2" for="email">Source
					System:</label>
				<div class="col-sm-2">
					<select class="form-control" id="sourceSystem">
						<option value="JB">JB Source System</option>
						<option value="SV">Signature Verication System</option>
						

					</select>

				</div>
				<div class=" col-sm-1">
					<label class="control-label" for="email">Doc No</label>
				</div>
				<div class=" col-sm-3">
					<input type="text" id="docRef" class="form-control"
						placeholder="Enter the document ref here">
				</div>

				<div>

					<button type="submit" id="btn-search" class="btn btn-success">
						<span class="glyphicon glyphicon-search"></span>Search
					</button>

				</div>

			</div>

		</form>



	</div>

	<div class="container">

		<div id="search-results"></div>

	</div>

	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="webjars/jquery-blockui/2.65/jquery.blockUI.js"></script>
	<script>
		jQuery(document).ready(function() {

			$("#search-form").submit(function(event) {

				// Disble the search button
				enableSearchButton(false);

				// Prevent the form from submitting via the browser.
				event.preventDefault();

				searchViaAjax();

			});

		});

		function enableSearchButton(flag) {
			$("#btn-search").prop("disabled", flag);
		}

		function searchViaAjax() {

			var search = {}
			search["docRef"] = $("#docRef").val();
			search["sourceSystem"] = $("#sourceSystem").val();

			$.blockUI({
				css : {
					border : 'none',
					padding : '15px',
					backgroundColor : '#000',
					'-webkit-border-radius' : '10px',
					'-moz-border-radius' : '10px',
					opacity : .2,
					color : '#fff'
				}
			});

			$
					.ajax({
						type : "POST",
						//contentType : "application/json",
						contentType : "application/x-www-form-urlencoded; charset=UTF-8", // this is the default value, so it's optional
						url : "dummy",
						//data : JSON.stringify(search),
						data : search,
						dataType : 'html',
						timeout : 100000,
						success : function(data) {
							console.log("SUCCESS: ", data);
							$('#search-results').html(data);
							$.unblockUI();

						},
						error : function(e) {
							console.log("ERROR: ", e);

						},
						done : function(e) {
							console.log("DONE");
							enableSearchButton(true);
						}
					});

		}
	</script>





</body>
</html>