<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<div class="row">
		<div class="col"></div>
		<div class="col-lg-3 col-sm-6 ml-auto mr-auto mt-3">
			<form method="post" class="base-form" enctype="multipart/form-data"
				onsubmit="return validatePlaylistEditCreateForm();" novalidate>

				<div class="form-group">

					<div class="base-form-title">
						<span>Playlist creation</span>
					</div>
					
					<div class="base-form-control d-none">
						<label for="inputLogin">Login</label>
						<input type="text" class="form-control" id="inputLogin" name="login"
							maxlength="20" placeholder="Enter login" value="${user.getLogin() }" required>
						<div class="invalid-feedback" id="inputLoginFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputName">Playlist name</label>
						<input type="text" class="form-control" id="inputName" name="name"
							maxlength="100" placeholder="Enter playlist name" required>
						<div class="invalid-feedback" id="inputNameFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputImageFile">Playlist cover</label>
						<input type="file" class="form-control-file custom-file"
							id="inputImageFile" name="cover"> 
						<div class="invalid-feedback" id="inputImageFileFeedback"></div>
					</div>

					<div class="base-form-submits">
						<div class="base-form-control">
							<button class="btn" type="submit">CREATE</button>
						</div>
					</div>


				</div>

			</form>

		</div>
		<div class="col"></div>

	</div>

</t:base_page>