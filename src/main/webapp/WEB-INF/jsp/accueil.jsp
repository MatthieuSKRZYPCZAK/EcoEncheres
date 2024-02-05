<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>ÉcoEnchères - Accueil</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp"%>
	<main>
		<div class="container mt-1">
			<div class="row justify-content-center text-center">
				<c:choose>
					<c:when test="${not empty requestScope.erreur}">
						<strong class="erreur"><c:out
								value="${requestScope.erreur}" /></strong>
					</c:when>
					<c:when test="${not empty requestScope.successMessage}">
						<strong class="succes"><c:out
								value="${requestScope.successMessage}" /></strong>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${not empty sessionScope.erreur}">
						<strong class="erreur"><c:out
								value="${sessionScope.erreur}" /></strong>
						<c:remove var="erreur" scope="session" />
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${not empty sessionScope.successMessage}">
						<strong class="succes"><c:out
								value="${sessionScope.successMessage}" /></strong>
						<c:remove var="successMessage" scope="session" />

					</c:when>
				</c:choose>

			</div>
		</div>

		<h1>Accueil - ÉcoEnchères</h1>


	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>