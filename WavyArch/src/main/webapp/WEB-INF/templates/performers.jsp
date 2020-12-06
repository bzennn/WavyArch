<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>
	<!-- Song List header-->
	<div class="row audio-list-header">
		<div class="col">
			<span class="audio-list-title">PERFORMERS</span>
			<span>:</span>
			<span class="audio-list-size">${accountPerformers.size()}
				performers</span>
		</div>

		<div class="col d-flex justify-content-end">

			<div class="btn-group">
				<button href="#" class="btn dropdown-toggle" type="button"
					id="sortMenu" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">SORT LIST</button>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="sortMenu">
					<!-- Sort by name -->
					<c:url var="byNameAsc" value="">
						<c:param name="sort" value="name" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byNameAsc}" class="dropdown-item">
						NAME
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>

					<c:url var="byNameDesc" value="">
						<c:param name="sort" value="name" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byNameDesc}" class="dropdown-item">
						NAME
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
					
					<!-- Sort by date -->
					<c:url var="byDateAsc" value="">
						<c:param name="sort" value="date" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byDateAsc}" class="dropdown-item">
						DATE
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>

					<c:url var="byDateDesc" value="">
						<c:param name="sort" value="date" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byDateDesc}" class="dropdown-item">
						DATE
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- Song List header-->

	<!-- Albums List -->
	<div
		class="row row-cols-lg-5 row-cols-md-4 row-cols-2 collection-cards">
		<c:forEach var="performer" items="${accountPerformers}">
			<div class="col mb-4">
				<a href="<c:url value="/performers/performer/${performer.getName()}" />"
					class="collection-item">
					<div class="card author">
						<c:if test="${not empty performer.getImagePath() }">
							<img
								src="<c:url value="/files/performers/${ performer.getImagePath() }"/>"
								alt="performerCover" class="card-img-top rounded-circle">
						</c:if>

						<c:if test="${empty performer.getImagePath() }">
							<img src="<c:url value="/resources/img/author.png"/>"
								alt="performerCover" class="card-img-top rounded-circle">
						</c:if>

						<div class="overlay">
							<div class="overlay-background"></div>
							<div class="overlay-info">
								<div class="overlay-info-title text-truncate">${performer.getName()}</div>
							</div>
						</div>
					</div>
				</a>
			</div>
		</c:forEach>
	</div>
	<!-- Albums List end-->
</t:base_page>