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
<link href="webjars/dropzone/4.3.0/min/dropzone.min.css"
	rel="stylesheet" />
<link
	href="webjars/bootstrap-dialog/1.34.6/dist/css/bootstrap-dialog.min.css"
	rel="stylesheet" />

<link rel="shortcut icon" type="image/x-icon"
	href="resources/images/favicon.ico" />





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

				<li class="active"><a href="index">Document Upload</a></li>
				<li><a href="viewer">Document Viewer</a></li>

			</ul>
		</div>
	</nav>


	<br />

	<br />
	<div class="container">


		<div class="row">

			<div class="col-md-12">







				<!--        Dropzone         -->
				<div style="margin-top: 70px" id="myId" class="dropzone"></div>

				<br />

				<div class="col-md-offset-5">

					<!-- Submit button-->
					<input type="submit" class=" btn btn-success" id="add" name="add"
						value="Upload Files">
				</div>


			</div>

		</div>

	</div>




	<script src="webjars/dropzone/4.3.0/min/dropzone.min.js"></script>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>

	<script src="webjars/jquery-blockui/2.65/jquery.blockUI.js"></script>

	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script
		src="webjars/bootstrap-dialog/1.34.6/dist/js/bootstrap-dialog.min.js"></script>
	<script type="text/javascript">
		Dropzone.autoDiscover = false;
		// unblock when ajax activity stops 

		jQuery(document)
				.ready(

						function() {
							var myDropzone = new Dropzone(
									"#myId",
									{
										url : 'upload',
										parallelUploads : 10000,

										uploadMultiple : true,
										dictDefaultMessage : "Kindly drop files here or click to upload",
										addRemoveLinks : true,
										autoProcessQueue : false
									});

							myDropzone
									.on(
											"errormultiple",
											function(files, response) {
												alert("Errors were detected whiles uploading files");
											});

							myDropzone.on("complete", function(file) {
								//	myDropzone.removeFile(file);
							});

							myDropzone.on("successmultiple", function(files,
									serverResponse) {

								$.unblockUI();

								BootstrapDialog.show({
									title : '<b>Info</b>',
									closeByBackdrop : false,
									closeByKeyboard : false,
									closable : false,
									draggable : true,

									type : BootstrapDialog.TYPE_SUCCESS,
									message : serverResponse.response,
									buttons : [ {
										label : 'OK',
										action : function(dialogRef) {
											dialogRef.close();
											myDropzone.removeAllFiles();
										}
									} ]
								});
								//	myDropzone.removeAllFiles();
							});

							$('#add').on('click', function(e) {
								console.log(myDropzone.files.length);
								if (myDropzone.files.length > 0) {

									e.preventDefault();
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
									myDropzone.processQueue();

								}
							}

							);

						});
	</script>

</body>
</html>