<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<!-- Song List header-->
	<div class="row audio-list-header">
		<div class="col">
			<span class="audio-list-title">MY AUDIOS</span>
			<span>:</span>
			<span class="audio-list-size">${accountAudios.size()} tracks</span>
		</div>

		<div class="col d-flex justify-content-end">

			<div class="btn-group mr-4">
				<button class="btn dropdown-toggle" type="button" id="sortMenu"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">SORT
					LIST</button>
				<div class="dropdown-menu" aria-labelledby="sortMenu">
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
					
					<!-- Sort by duration -->
					<c:url var="byDurationAsc" value="">
						<c:param name="sort" value="duration" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byDurationAsc}" class="dropdown-item">
						DURATION
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					
					<c:url var="byDurationDesc" value="">
						<c:param name="sort" value="duration" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byDurationDesc}" class="dropdown-item">
						DURATION
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
					
					<!-- Sort by performers -->
					<c:url var="byPerformersAsc" value="">
						<c:param name="sort" value="performers" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byPerformersAsc}" class="dropdown-item">
						PERFORMERS
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					
					<c:url var="byPerformersDesc" value="">
						<c:param name="sort" value="performers" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byPerformersDesc}" class="dropdown-item">
						PERFORMERS
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
					
					<!-- Sort by authors -->
					<c:url var="byAuthorsAsc" value="">
						<c:param name="sort" value="authors" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byAuthorsAsc}" class="dropdown-item">
						AUTHORS
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					
					<c:url var="byAuthorsDesc" value="">
						<c:param name="sort" value="authors" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byAuthorsDesc}" class="dropdown-item">
						AUTHORS
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
					
					<!-- Sort by album -->
					<c:url var="byAlbumAsc" value="">
						<c:param name="sort" value="album" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byAlbumAsc}" class="dropdown-item">
						ALBUM
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					
					<c:url var="byAlbumDesc" value="">
						<c:param name="sort" value="album" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byAlbumDesc}" class="dropdown-item">
						ALBUM
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
					
					<!-- Sort by genre -->
					<c:url var="byGenreAsc" value="">
						<c:param name="sort" value="genre" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byGenreAsc}" class="dropdown-item">
						GENRE
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					
					<c:url var="byGenreDesc" value="">
						<c:param name="sort" value="genre" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byGenreDesc}" class="dropdown-item">
						GENRE
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
					
					<!-- Sort by tags -->
					<c:url var="byTagsAsc" value="">
						<c:param name="sort" value="tags" />
						<c:param name="order" value="asc" />
					</c:url>
					<a href="${byTagsAsc}" class="dropdown-item">
						TAGS
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					
					<c:url var="byTagsDesc" value="">
						<c:param name="sort" value="tags" />
						<c:param name="order" value="desc" />
					</c:url>
					<a href="${byTagsDesc}" class="dropdown-item">
						TAGS
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>
					
				</div>
			</div>

			<a href="audios/upload" class="btn">UPLOAD AUDIO</a>
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
					<c:forEach var="audio" items="${accountAudios}">
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
								<a
									href="<c:url value="/search?request=${genre}&category=genres"/>">${genre}</a>
							</td>
							<td>
								<c:forEach var="tag" items="${tags}">
									<a
										href="<c:url value="/search?request=${tag.getName()}&category=tags"/>">
										<span class="badge">${tag.getName()}</span>
									</a>
								</c:forEach>
							</td>
							<td>
								<div class="row">
									<div class="col-3">
										<a
											href="<c:url value="/recommendations/${audio.getName()}" />"
											class="">
											<i class="fas fa-star"></i>
										</a>
									</div>
									<div class="col-3">
										<a href="<c:url value="/files/audios/${filePath}"/>" class="" download>
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
										<a href="<c:url value="/audios/delete/${audio.getName()}" />"
											class="">
											<i class="fas fa-times"></i>
										</a>
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