<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<!-- Song List header-->
	<div class="row audio-list-header">
		<div class="col">
			<span class="audio-list-title">RECOMMENDATIONS</span>
			<span>:</span>
			<span class="audio-list-size">found ${recommendationsAudios.size()} tracks</span>
			
			<div class="search-request">For audio: "<span class="search-request-text text-wrap">${audioName}</span>"</div>
		</div>
	</div>
	<!-- Song List header-->

	<!-- Song List -->
	<div class="row">
		<div class="col audio-list-block table-responsive">
			<table class="table table-striped audio-list-table">
				<thead class="audio-list-table-head">
					<tr>
						<th scope="col" style="width: 2%;">#</th>
						<th scope="col" style="width: 23%;">NAME</th>
						<th scope="col" style="width: 5%;">
							<i class="far fa-clock"></i>
						</th>
						<th scope="col" style="width: 15%;">PERFORMERS</th>
						<th scope="col" style="width: 15%;">AUTHORS</th>
						<th scope="col" style="width: 15%;">ALBUM</th>
						<th scope="col" style="width: 5%;">GENRE</th>
						<th scope="col" style="width: 10%;">TAGS</th>
						<th scope="col" style="width: 10%;">ACTIONS</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="index" scope="page" value="0" />
					<c:forEach var="audio" items="${recommendationsAudios}">
						<c:set var="index" scope="page" value="${index + 1}" />
						<c:set var="genre" scope="page"
							value="${audio.getGenre().getName()}" />
						<c:set var="filePath" scope="page" value="${audio.getFilePath()}" />
						<c:set var="album" scope="page"
							value="${audio.getAlbum().getName()}" />
						<c:set var="performers" scope="page"
							value="${audio.getPerformers()}" />
						<c:set var="authors" scope="page" value="${audio.getAuthors()}" />
						<c:set var="tags" scope="page" value="${audio.getTags()}" />

						<tr>
							<td>${index}</td>
							<td>${audio.getName()}</td>
							<td>${audio.formatDuration()}</td>
							<td>
								<c:forEach var="audioPerformer" items="${performers}">
									<c:set var="performer" scope="page"
										value="${audioPerformer.getAudioMaker().getName()}" />
									<a href="<c:url value="/performers/performer/${performer}"/>">${performer}</a>
								</c:forEach>
							</td>
							<td>
								<c:forEach var="audioAuthor" items="${authors}">
									<c:set var="author" scope="page"
										value="${audioAuthor.getAudioMaker().getName()}" />
									<a href="<c:url value="/performers/performer/${author}"/>">${author}</a>
								</c:forEach>
							</td>
							<td>
								<a href="<c:url value="/albums/album/${album}" />">${album}</a>
							</td>
							<td>
								<a href="<c:url value="/search?request=${genre}&category=genres"/>">${genre}</a>
							</td>
							<td>
								<c:forEach var="tag" items="${tags}">
									<a href="<c:url value="/search?request=${tag.getName()}&category=tags"/>">
										<span class="badge">${tag.getName()}</span>
									</a>
								</c:forEach>
							</td>
							<td>
								<div class="row">
									<div class="col-3">
										<a href="<c:url value="/recommendations/${audio.getName()}" />" class="">
											<i class="fas fa-star"></i>
										</a>
									</div>
									<div class="col-3">
										<a href="<c:url value="/files/audios/${filePath}" />" class="" download>
											<i class="fas fa-download"></i>
										</a>
									</div>
									<div class="col-3">
										<a href="<c:url value="/audios/edit/${audio.getName()}" />"
											class="">
											<i class="far fa-edit"></i>
										</a>
									</div>
									<div class="col-3">
										<c:if test="${audiosNotInAccount.contains(audio.getName())}">
											<a
												href="<c:url value="/audios/addToAccount/${audio.getName()}" />"
												class="">
												<i class="fas fa-plus-circle"></i>
											</a>
										</c:if>
									</div>
								</div>
							</td>
						</tr>

					</c:forEach>

					<tr class="emptyRow"></tr>

				</tbody>
			</table>
		</div>
	</div>
	<!-- Song List end-->

</t:base_page>