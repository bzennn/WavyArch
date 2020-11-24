<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>

<t:base_page>
	<div class="row">
		<div class="col"></div>
		<div class="col-lg-3 col-sm-6 ml-auto mr-auto mt-3">

			<div class="error">
				<div class="error-title">Error ${errorCode}</div>
				<div class="error-note">${errorMessage}!</div>
				<a href="<c:url value="/"/>" class="btn mt-4">Back to main page</a>
			</div>

		</div>
		<div class="col"></div>
	</div>
</t:base_page>