<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<form method="post" class="base-form mt-3"
		enctype="multipart/form-data"
		onsubmit="return validateAlbumEditForm();" novalidate>

		<div class="row">
			<div class="col d-flex justify-content-center">
				<div class="base-form-title">Edit</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xl-3 col-lg-4 col-md-4 col-5">
				<div class="card">
					<c:if test="${not empty album.getImagePath() }">
						<img
							src="<c:url value="/files/albums/${ album.getImagePath() }"/>"
							alt="albumCover" class="card-img-top rounded-bottom rounded-top">
					</c:if>

					<c:if test="${empty album.getImagePath() }">
						<img src="<c:url value="/resources/img/album.jpg"/>"
							alt="albumCover" class="card-img-top rounded-bottom rounded-top">
					</c:if>
				</div>
			</div>

			<div class="col">
				<div class="form-group">
					<span class="edit-type">ALBUM:</span>
					<span class="edit-target">${album.getName()}</span>

					<div class="base-form-control d-none">
						<label for="inputName">Name</label>
						<input type="text" class="form-control" id="inputName" name="name"
							maxlength="200" placeholder="Enter name"
							value="${album.getName()}" required>
						<div class="invalid-feedback" id="inputNameFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputImageFile">Album cover</label>
						<input type="file" class="form-control-file custom-file"
							id="inputImageFile" name="cover">
						<div class="invalid-feedback" id="inputImageFileFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputCreationDate">Creation Date</label>
						<input type="month" class="form-control" id="inputCreationDate" name="creationDate"
							value="${creationDate}">
						<div class="invalid-feedback" id="inputCreationDateFeedback"></div>
					</div>
					
					<div class="base-form-control">
						<label for="inputAuthor">Author</label>
						<input type="text" class="form-control" id="inputAuthor" name="author"
							maxlength="60" placeholder="Enter author"
							value="${author}">
						<div class="invalid-feedback" id="inputAuthorFeedback"></div>
					</div>

				</div>
			</div>
		</div>
		<div class="row">
			<div class="col d-flex justify-content-center">
				<div class="base-form-submits">
					<div class="base-form-control">
						<button class="btn save-btn" type="submit">SAVE</button>
					</div>
				</div>
			</div>
		</div>

	</form>

</t:base_page>