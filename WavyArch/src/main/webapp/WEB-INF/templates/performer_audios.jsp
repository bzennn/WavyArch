<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>
	<!-- Song List header-->
	<div class="row audio-list-header">

		<div class="col-xl-1 col-lg-2 col-md-2 col-3">
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
			</div>
		</div>

		<div class="col">
			<span class="audio-list-title text-truncate">${performer.getName()}</span>
			<span>:</span>
			<span class="audio-list-size">${performerAudios.size()} tracks</span>
			<div class="text-wrap">${performer.getDescription()}</div>
		</div>

		<div class="col d-flex justify-content-end">

			<div class="btn-group mr-4">
				<div class="dropdown mr-4">
					<button class="btn dropdown-toggle" type="button" id="filterMenu"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">FILTER
						LIST</button>
					<div class="dropdown-menu dropdown-menu-right dropdown-menu-form"
						aria-labelledby="filterMenu">
						<form method="get" action="" class="dropdown-form">
							<c:if test="${ not empty param.sort }">
								<input type="hidden" maxlength="100" name="sort" value="${param.sort}">
							</c:if>
							<c:if test="${ not empty param.order }">
								<input type="hidden" maxlength="100" name="order" value="${param.order}">
							</c:if>
							
							<div class="dropdown-form-control">
								<label>Performers</label>
								<input type="text" class="form-control" id="inputName"
									placeholder="Performers semicolon separated" maxlength="1000"
									name="performers">
								<div class="invalid-feedback" id="inputNameFeedback"></div>
							</div>
							<div class="dropdown-form-control">
								<label>Authors</label>
								<input type="text" class="form-control" id="inputName"
									placeholder="Authors semicolon separated" maxlength="1000"
									name="authors">
								<div class="invalid-feedback" id="inputNameFeedback"></div>
							</div>
							<div class="dropdown-form-control">
								<label>Albums</label>
								<input type="text" class="form-control" id="inputName"
									placeholder="Albums semicolon separated" maxlength="1000"
									name="albums">
								<div class="invalid-feedback" id="inputNameFeedback"></div>
							</div>
							<div class="dropdown-form-control">
								<label>Genres</label>
								<input type="text" class="form-control" id="inputName"
									placeholder="Genres semicolon separated" maxlength="1000"
									name="genres">
								<div class="invalid-feedback" id="inputNameFeedback"></div>
							</div>
							<div class="dropdown-form-control">
								<label>Tags</label>
								<input type="text" class="form-control" id="inputName"
									placeholder="Tags semicolon separated" maxlength="1000"
									name="tags">
								<div class="invalid-feedback" id="inputNameFeedback"></div>
							</div>
							<div class="dropdown-form-control">
								<label>Creation year from</label>
								<input type="text" class="form-control" id="inputName"
									placeholder="Creation year from" maxlength="5" name="yearFrom">
								<div class="invalid-feedback" id="inputNameFeedback"></div>
							</div>
							<div class="dropdown-form-control">
								<label>Creation year to</label>
								<input type="text" class="form-control" id="inputName"
									placeholder="Creation year to" maxlength="5" name="yearTo">
								<div class="invalid-feedback" id="inputNameFeedback"></div>
							</div>
							<div class="dropdown-form-control">
								<button class="btn save-btn" type="submit">FILTER</button>
							</div>
						</form>
					</div>
				</div>
				<div class="dropdown">
					<button class="btn dropdown-toggle" type="button" id="sortMenu"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">SORT
						LIST</button>
					<div class="dropdown-menu" aria-labelledby="sortMenu">
						<!-- Sort by name -->
						<c:url var="byNameAsc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
							
							<c:param name="sort" value="name" />
							<c:param name="order" value="asc" />
						</c:url>
						<a href="${byNameAsc}" class="dropdown-item">
							NAME
							<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
						</a>

						<c:url var="byNameDesc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="name" />
							<c:param name="order" value="desc" />
						</c:url>
						<a href="${byNameDesc}" class="dropdown-item">
							NAME
							<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
						</a>

						<!-- Sort by duration -->
						<c:url var="byDurationAsc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="duration" />
							<c:param name="order" value="asc" />
						</c:url>
						<a href="${byDurationAsc}" class="dropdown-item">
							DURATION
							<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
						</a>

						<c:url var="byDurationDesc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
							
							<c:param name="sort" value="duration" />
							<c:param name="order" value="desc" />
						</c:url>
						<a href="${byDurationDesc}" class="dropdown-item">
							DURATION
							<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
						</a>

						<!-- Sort by performers -->
						<c:url var="byPerformersAsc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="performers" />
							<c:param name="order" value="asc" />
						</c:url>
						<a href="${byPerformersAsc}" class="dropdown-item">
							PERFORMERS
							<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
						</a>

						<c:url var="byPerformersDesc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="performers" />
							<c:param name="order" value="desc" />
						</c:url>
						<a href="${byPerformersDesc}" class="dropdown-item">
							PERFORMERS
							<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
						</a>

						<!-- Sort by authors -->
						<c:url var="byAuthorsAsc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="authors" />
							<c:param name="order" value="asc" />
						</c:url>
						<a href="${byAuthorsAsc}" class="dropdown-item">
							AUTHORS
							<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
						</a>

						<c:url var="byAuthorsDesc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="authors" />
							<c:param name="order" value="desc" />
						</c:url>
						<a href="${byAuthorsDesc}" class="dropdown-item">
							AUTHORS
							<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
						</a>

						<!-- Sort by album -->
						<c:url var="byAlbumAsc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="album" />
							<c:param name="order" value="asc" />
						</c:url>
						<a href="${byAlbumAsc}" class="dropdown-item">
							ALBUM
							<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
						</a>

						<c:url var="byAlbumDesc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="album" />
							<c:param name="order" value="desc" />
						</c:url>
						<a href="${byAlbumDesc}" class="dropdown-item">
							ALBUM
							<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
						</a>

						<!-- Sort by genre -->
						<c:url var="byGenreAsc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="genre" />
							<c:param name="order" value="asc" />
						</c:url>
						<a href="${byGenreAsc}" class="dropdown-item">
							GENRE
							<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
						</a>

						<c:url var="byGenreDesc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="genre" />
							<c:param name="order" value="desc" />
						</c:url>
						<a href="${byGenreDesc}" class="dropdown-item">
							GENRE
							<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
						</a>

						<!-- Sort by tags -->
						<c:url var="byTagsAsc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="tags" />
							<c:param name="order" value="asc" />
						</c:url>
						<a href="${byTagsAsc}" class="dropdown-item">
							TAGS
							<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
						</a>

						<c:url var="byTagsDesc" value="">
							<c:if test="${ not empty param.performers }">
								<c:param name="performers" value="${ param.performers }" />
							</c:if>
							<c:if test="${ not empty param.authors }">
								<c:param name="authors" value="${ param.authors }" />
							</c:if>
							<c:if test="${ not empty param.albums }">
								<c:param name="albums" value="${ param.albums }" />
							</c:if>
							<c:if test="${ not empty param.genres }">
								<c:param name="genres" value="${ param.genres }" />
							</c:if>
							<c:if test="${ not empty param.tags }">
								<c:param name="tags" value="${ param.tags }" />
							</c:if>
							<c:if test="${ not empty param.yearFrom }">
								<c:param name="yearFrom" value="${ param.yearFrom }" />
							</c:if>
							<c:if test="${ not empty param.yearTo }">
								<c:param name="yearTo" value="${ param.yearTo }" />
							</c:if>
						
							<c:param name="sort" value="tags" />
							<c:param name="order" value="desc" />
						</c:url>
						<a href="${byTagsDesc}" class="dropdown-item">
							TAGS
							<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
						</a>

					</div>
				</div>
			</div>

			<a
				href="<c:url value="/performers/performer/edit/${performer.getName()}"/>"
				class="btn">EDIT PERFORMER</a>
		</div>
	</div>
	<!-- Song List header-->

	<!-- Song List -->
	<div class="row">
		<div class="col audio-list-block table-responsive">
			<table class="table table-striped audio-list-table">
				<thead class="audio-list-table-head">
					<tr>
						<th scope="col" style="width: 5%;">#</th>
						<th scope="col" style="width: 23%;">NAME</th>
						<th scope="col" style="width: 3%;">
							<i class="far fa-clock"></i>
						</th>
						<th scope="col" style="width: 15%;">PERFORMERS</th>
						<th scope="col" style="width: 14%;">AUTHORS</th>
						<th scope="col" style="width: 15%;">ALBUM</th>
						<th scope="col" style="width: 5%;">GENRE</th>
						<th scope="col" style="width: 10%;">TAGS</th>
						<th scope="col" style="width: 10%;">ACTIONS</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="index" scope="page" value="0" />
					<c:forEach var="audio" items="${performerAudios}">
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

						<tr class="audio-record" id="audio-record-${index}">
							<td style="min-width:80px">
								<div class="audio-id">${index}</div>
								<div class="fas fa-play fa-fw mt-1 playChoosedButton">
									<i class="d-none">audio-record-${index}</i>
								</div>
							</td>
							<td id="audio-name">${audio.getName()}</td>
							<td>${audio.formatDuration()}</td>
							<td id="audio-performers">
								<c:forEach var="audioPerformer" items="${performers}">
									<c:set var="performer" scope="page"
										value="${audioPerformer.getAudioMaker().getName()}" />
									<a href="<c:url value="/performers/performer/${performer}"/>"
										id="audio-performer">${performer}</a>
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
								<div class="row mr-2">
									<div class="col-3">
										<a
											href="<c:url value="/recommendations/${audio.getName()}" />"
											class="">
											<i class="fas fa-star"></i>
										</a>
									</div>
									<div class="col-3">
										<a href="<c:url value="/files/audios/${filePath}"/>" class=""
											id="audio-link" download>
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