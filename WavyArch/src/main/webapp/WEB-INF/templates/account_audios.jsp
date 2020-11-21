<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<!-- Song List header-->
	<div class="row audio-list-header">
		<div class="col">
			<span class="audio-list-title">MY AUDIOS</span>
			<span>:</span>
			<span class="audio-list-size">30 tracks</span>
		</div>

		<div class="col d-flex justify-content-end">

			<div class="btn-group mr-4">
				<button class="btn dropdown-toggle" type="button"
					id="sortMenu" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">SORT LIST</button>
				<div class="dropdown-menu" aria-labelledby="sortMenu">
					<a href="#" class="dropdown-item">
						CRITERIA 1
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					<a href="#" class="dropdown-item">
						CRITERIA 1
						<i class="fas fa-sort-amount-up fa-fw pl-1"></i>
					</a>

					<a href="#" class="dropdown-item">
						CRITERIA 2
						<i class="fas fa-sort-amount-down fa-fw pl-1"></i>
					</a>
					<a href="#" class="dropdown-item">
						CRITERIA 2
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

					<tr>
						<td>1</td>
						<td>
							Bohemian Rhapsody
						</td>
						<td>5:55</td>
						<td>
							<a href="#">Queen</a>
						</td>
						<td>
							<a href="#">Freddie Mercury</a>
							<a href="#">Brian May</a>
							<a href="#">Roger Taylor</a>
							<a href="#">John Deacon</a>
						</td>
						<td>
							<a href="#">A Night at the Opera</a>
						</td>
						<td>
							<a href="#">Rock</a>
						</td>
						<td>
							<a href="#">
								<span class="badge">Rock</span>
							</a>
							<a href="#">
								<span class="badge">Glam</span>
							</a>
							<a href="#">
								<span class="badge">Vocal</span>
							</a>
							<a href="#">
								<span class="badge">Instumental</span>
							</a>
							<a href="#">
								<span class="badge">Classic</span>
							</a>
						</td>
						<td>
							<div class="row">
								<div class="col-4">
									<a href="#" class="">
										<i class="fas fa-download"></i>
									</a>
								</div>
								<div class="col-4">
									<a href="#" class="">
										<i class="far fa-edit"></i>
									</a>
								</div>
								<div class="col-4">
									<a href="#" class="">
										<i class="fas fa-times"></i>
									</a>
								</div>
							</div>
						</td>
					</tr>




					<tr class="emptyRow"></tr>

				</tbody>
			</table>
		</div>
	</div>
	<!-- Song List end-->

</t:base_page>