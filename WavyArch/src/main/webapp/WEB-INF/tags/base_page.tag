<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ attribute name="user" required="false"%>

<t:skeleton_page>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-md sticky-top">
		<div class="container-fluid">

			<!-- Logo -->
			<div class="navbar-brand">
				<a href="<c:url value="/"/>">
					<img class="logo-img"
						src="<c:url value="/resources/img/logo.svg"/>" height="25"
						alt="Logo">
					<span class="logo-name">WavyArch</span>
				</a>
			</div>
			<!-- Logo end -->

			<!-- Navbar adaptive toggler -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navBarMenu" aria-controls="navBarMenu"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="fas fa-bars"></span>
			</button>
			<!-- Navbar adaptive toggler end -->

			<!-- Navbar items to be hidden -->
			<div class="collapse-md navbar-collapse" id="navBarMenu">

				<!-- Sidebar toggler -->
				<div class="nav-item d-lg-block d-none ml-2">
					<div class="btn sidebar-toggle-btn" id="sidebar-toggle-btn">
						<i class="fas fa-bars"></i>
					</div>
				</div>
				<!-- Sidebar toggler end -->

				<!-- Search form -->
				<c:if test="${not empty user }">
					<div class="nav-item ml-auto mr-auto">
						<form action="<c:url value="/search"/>" method="get"
							class="form-inline" onsubmit="return validateSearchRequest();"
							novalidate>
							<input class="form-control" type="text" name="request"
								placeholder="Search" maxlength="400" id="inputSearch" required>
							<button class="btn search-btn" type="submit">
								<i class="fas fa-search"></i>
							</button>
						</form>
					</div>
				</c:if>
				<!-- Search form end -->
			</div>
			<!-- Navbar items to be hidden end -->
		</div>

	</nav>
	<!-- Navbar end -->

	<!-- Main container -->
	<div class="container-fluid">
		<div class="row">

			<!-- Sidebar -->
			<div
				class="sidebar-container col-xl-2 col-lg-3 col-md-3 d-lg-block d-none"
				id="sidebar-container">
				<div class="list-group sticky-top sticky-offset">

					<div class="list-group-label">ACCOUNT</div>

					<c:choose>
						<c:when test="${not empty user}">
							<div class="menu-authorized">
								<div class="list-group-account container">
									<div class="row">
										<div class="col-4">
											<c:if test="${not empty user.getImagePath() }">
												<img
													src="<c:url value="/files/images/${ user.getImagePath() }"/>"
													alt="avatar" class="rounded-circle">
											</c:if>

											<c:if test="${empty user.getImagePath() }">
												<img src="<c:url value="/resources/img/avatar.png"/>"
													alt="avatar" class="rounded-circle">
											</c:if>
										</div>
										<div class="col-8">
											<span class="login">${ user.getLogin() }</span>
											<div class="role-name">${ user.getRole().getName() }</div>
										</div>
									</div>
									<div class="row">
										<div class="col-6">
											<a href="<c:url value="/account-edit"/>" class="btn">Edit</a>
										</div>
										<div class="col-6">
											<a href="<c:url value="/signout"/>" class="btn">Exit</a>
										</div>
									</div>
								</div>
							</div>

							<div class="menu-tabs">

								<div class="list-group-label">MENU</div>
								<a href="<c:url value="/audios"/>"
									class="list-group-item <c:if test="${viewName == 'audios' }">active</c:if>">
									<i class="fab fa-itunes-note"></i>
									<small> AUDIOS</small>
								</a>

								<a href="<c:url value="/albums"/>"
									class="list-group-item <c:if test="${viewName == 'albums' }">active</c:if>">
									<i class="fas fa-record-vinyl"></i>
									<small>ALBUMS</small>
								</a>

								<a href="<c:url value="/performers"/>"
									class="list-group-item <c:if test="${viewName == 'performers' }">active</c:if>">
									<i class="fas fa-guitar"></i>
									<small>PERFORMERS</small>
								</a>

								<a href="<c:url value="/playlists"/>"
									class="list-group-item <c:if test="${viewName == 'playlists' }">active</c:if>">
									<i class="fas fa-headphones-alt"></i>
									<small>PLAYLISTS</small>
								</a>

							</div>
						</c:when>

						<c:when test="${empty user }">
							<div class="menu-unauthorized">

								<a href="<c:url value="/signin"/>"
									class="list-group-item <c:if test="${viewName == 'signin' }">active</c:if>">
									<i class="fas fa-sign-in-alt"></i>
									<small> SIGN IN</small>
								</a>

								<a href="<c:url value="/signup"/>"
									class="list-group-item <c:if test="${viewName == 'signup' }">active</c:if>">
									<i class="fas fa-user-plus"></i>
									<small>SIGN UP</small>
								</a>

							</div>
						</c:when>
					</c:choose>

					<div class="menu-about d-none">

						<div class="list-group-label">ABOUT</div>
						<div class="about">
							<span class="about-item">Music library web server</span>
							<span class="about-item">License: MIT</span>
						</div>


					</div>

					<div class="menu-copyright ">

						<div class="list-group-label">COPYRIGHT</div>
						<div class="copyright">
							<span>Bzennn © 2020.</span>
							<span>All rights reserved</span>
						</div>

					</div>
				</div>

			</div>
			<!-- Sidebar end -->

			<!-- Content -->
			<div class="col content">
				<div class="row">
					<div class="col">
						<c:if test="${not empty errors}">
							<c:forEach items="${errors}" var="error">
								<div class="error-message mt-3">
									<span>${error.getDefaultMessage()}</span>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</div>

				<jsp:doBody />

			</div>
			<!-- Content end -->

		</div>
	</div>
	<!-- Main container end -->


	<c:if test="${ playerAvailable }">
		<!-- Audio player -->
		<div class="audio-player fixed-bottom d-none">
			<audio src="" id="audioContainer"></audio>

			<div class="row">
				<div class="col">
					<div class="audio-player-info">
						<div class="audio-player-title"></div>
						<div class="audio-player-performer"></div>
						<div class="d-none audio-current-id"></div>
					</div>
				</div>
				<div class="col-6">
					<div class="audio-player-controls d-flex justify-content-center">
						<div class="audio-control-btn mr-1" id="previousButton">
							<i class="fas fa-step-backward fa-fw"></i>
						</div>
						<div class="audio-control-btn mr-1" id="playButton">
							<i class="fas fa-play fa-fw"></i>
						</div>
						<div class="audio-control-btn mr-1 d-none" id="pauseButton">
							<i class="fas fa-pause fa-fw"></i>
						</div>
						<div class="audio-control-btn" id="nextButton">
							<i class="fas fa-step-forward fa-fw"></i>
						</div>
					</div>
				</div>
				<div class="col">
					<div class="audio-player-time d-flex justify-content-end">
						<input type="range" min="0" max="1" value="1" step="0.01"
							class="slider volume-range mr-2" id="volumeRange">
						<span class="fas fa-volume-up volume-icon mr-4"></span>
						<span class="audio-player-current mr-1" id="currentTime">00:00</span>
						<span class="delimiter mr-1">:</span>
						<span class="audio-player-whole" id="duration">00:00</span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="audio-player-timeline">
						<input type="range" min="0" max="10000" value="0" class="slider"
							id="progressRange">
					</div>
				</div>
			</div>
		</div>
		<!-- Audio player end -->
	</c:if>

</t:skeleton_page>