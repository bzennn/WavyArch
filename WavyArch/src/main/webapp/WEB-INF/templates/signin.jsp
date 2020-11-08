<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<div class="row">
		<div class="col"></div>
		<div class="col-lg-3 col-sm-6 ml-auto mr-auto mt-3">
			<form method="post" class="base-form"
				onsubmit="return validateSignInForm();" novalidate>

				<div class="form-group">

					<div class="base-form-title">
						<span>Sign In</span>
					</div>

					<div class="base-form-control">
						<label for="inputLogin">Login</label>
						<input type="text" class="form-control" id="inputLogin"
							name="login" placeholder="Enter login" maxlength="20" required>
						<div class="invalid-feedback" id="inputLoginFeedback"></div>
					</div>

					<div class="base-form-control">
						<label for="inputPassword">Password</label>
						<input type="password" class="form-control" id="inputPassword"
							name="password" placeholder="Enter password" maxlength="20" required>
						<div class="invalid-feedback" id="inputPasswordFeedback"></div>
					</div>

					<div class="base-form-submits">
						<div class="base-form-control">
							<button class="btn" type="submit">SIGN IN</button>
						</div>

						<div class="base-form-note">
							<span>OR</span>
						</div>

						<div class="base-form-control">
							<a href="<c:url value="signup"/>" class="btn">SIGN UP</a>
						</div>
					</div>


				</div>

			</form>

		</div>
		<div class="col"></div>

	</div>

</t:base_page>