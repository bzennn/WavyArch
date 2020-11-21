<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base_page>

	<form method="post" action="" class="base-form mt-3" enctype="multipart/form-data"
		onsubmit="return validateAudioUploadEditForm(true);" novalidate>

		<div class="row">
			<div class="col d-flex justify-content-center">
				<div class="base-form-title">Upload</div>
			</div>
		</div>

		<div class="row">
			<div class="col">
				<div class="form-group">
					<span class="edit-type">AUDIO:</span>
					<span class="edit-target">New</span>


					<table class="table table-borderless">
						<tr>
							<td style="width: 50%;">
								<div class="base-form-control">
									<label for="inputName">Name</label>
									<input type="text" class="form-control" id="inputName" name="name"
										placeholder="Enter name" maxlength="60" required>
									<div class="invalid-feedback" id="inputNameFeedback"></div>
								</div>

								<div class="base-form-control">
									<label for="inputAudioFile">File</label>
									<input type="file" class="form-control-file custom-file" name="audio"
										id="inputAudioFile" required>
									<div class="invalid-feedback" id="inputAudioFileFeedback"></div>
								</div>

								<div class="base-form-control">
									<label for="inputCreationDate">Creation Date</label>
									<input type="month" class="form-control" id="inputCreationDate" name="creationDate">
									<div class="invalid-feedback" id="inputCreationDateFeedback"></div>
								</div>

								<div class="base-form-control">
									<label for="inputGenre">Genre</label>
									<input type="text" class="form-control" id="inputGenre" name="genre"
										placeholder="Enter genre" maxlength="60">
									<div class="invalid-feedback" id="inputGenreFeedback"></div>
								</div>
							</td>
							<td style="width: 50%;">


								<div class="base-form-control">
									<label for="inputAlbum">Album</label>
									<input type="text" class="form-control" id="inputAlbum" name="album"
										placeholder="Enter album name" maxlength="200">
									<div class="invalid-feedback" id="inputAlbumFeedback"></div>
								</div>

								<div class="base-form-control">
									<label for="inputPerformers">Performers</label>
									<input type="text" class="form-control" id="inputPerformers" name="performers"
										placeholder="Enter performers semicolon separated"
										maxlength="1000">
									<div class="invalid-feedback" id="inputPerformersFeedback"></div>
								</div>

								<div class="base-form-control">
									<label for="inputAuthors">Authors</label>
									<input type="text" class="form-control" id="inputAuthors" name="authors"
										placeholder="Enter [author : role] semicolon separated"
										maxlength="1000">
									<div class="invalid-feedback" id="inputAuthorsFeedback"></div>
								</div>

								<div class="base-form-control">
									<label for="inputTags">Tags</label>
									<input type="text" class="form-control" id="inputTags" name="tags"
										placeholder="Enter tags semicolon separated" maxlength="400">
									<div class="invalid-feedback" id="inputTagsFeedback"></div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col d-flex justify-content-center">
				<div class="base-form-submits">
					<div class="base-form-control">
						<button class="btn save-btn" type="submit">UPLOAD</button>
					</div>
				</div>
			</div>
		</div>

	</form>

</t:base_page>