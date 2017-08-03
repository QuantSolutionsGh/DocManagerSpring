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
<script src="webjars/dropzone/4.3.0/min/dropzone.min.js"></script>
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Insert title here</title>
</head>
<body>

	<div class="container">

		<div class="row">

			<div class="col-md-12">



				<form action="upload.php" enctype="multipart/form-data"
					class="dropzone" id="image-upload">

					<div>


						<input class="btn btn-success" type="submit"
							value="Upload File to Server" id="btnUpload">
					</div>

				</form>

			</div>

		</div>

	</div>





	<script type="text/javascript">
		Dropzone.options.imageUpload = {

			parallelUploads : 10000,
			uploadMultiple : true,
			autoProcessQueue : false
			
			init: function() {
			    var submitButton = document.querySelector("#btnUpload")
			        myDropzone = this; // closure

			    submitButton.addEventListener("click", function() {
			    	
			      myDropzone.processQueue(); // Tell Dropzone to process all queued files.
			    });}

		};
	</script>

</body>
</html>