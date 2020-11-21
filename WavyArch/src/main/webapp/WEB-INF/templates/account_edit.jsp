<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<div class="row">
		<div class="col"></div>
		<div class="col-lg-3 col-sm-6 ml-auto mr-auto mt-3">
			<form method="post" class="base-form" enctype="multipart/form-data"
				onsubmit="return validateAccountEditForm();" novalidate>

				<div class="form-group">

					<div class="base-form-title">
						<span>Edit Account</span>
					</div>

					<div class="base-form-control d-none">
						<label for="inputLogin">Login</label>
						<input type="text" class="form-control" id="inputLogin" name="login"
							maxlength="20" value="${ user.getLogin() }">
					</div>

					<div class="base-form-control">
						<label for="inputOldPassword">Old password</label>
						<input type="password" class="form-control" id="inputOldPassword"
							name="oldPassword" maxlength="20" placeholder="Enter password">
						<div class="invalid-feedback" id="inputOldPasswordFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputNewPassword">New password</label>
						<input type="password" class="form-control" id="inputNewPassword"
							name="newPassword" maxlength="20" placeholder="Enter password">
						<div class="invalid-feedback" id="inputNewPasswordFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputRepeatNewPassword">Repeat new password</label>
						<input type="password" class="form-control"
							id="inputRepeatNewPassword" name="newPasswordRepeat"
							maxlength="20" placeholder="Enter password">
						<div class="invalid-feedback" id="inputRepeatNewPasswordFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputImageFile">Avatar</label>
						<input type="file" class="form-control-file custom-file"
							id="inputImageFile" name="avatar">
						<div class="invalid-feedback" id="inputImageFileFeedback"></div>
					</div>

					<div class="base-form-submits">
						<div class="base-form-control">
							<button class="btn" type="submit">SAVE</button>
						</div>
					</div>

				</div>

			</form>

		</div>
		<div class="col"></div>

	</div>

</t:base_page>