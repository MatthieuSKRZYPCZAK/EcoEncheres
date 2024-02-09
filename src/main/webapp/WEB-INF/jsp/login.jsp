<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
boolean seSouvenir = (boolean) request.getAttribute("seSouvenir");
%>

<title>ÉcoEnchères - Login</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp"%>
	<main>
		<div class="container mt-5">
			<div class="row justify-content-center">
				<div class="col-md-4">
					<h1 class="mb-4">Identification</h1>
					<form action="login" method="post">
						<div class="form-group">
							<label for="pseudo">Identifiant :</label> <input type="text"
								id="pseudo" name="pseudo" class="form-control"
								value="${lastLogin}" required>
						</div>
						<div class="form-group">
							<label for="password">Mot de passe :</label> <input
								type="password" id="password" name="password"
								class="form-control" required>
						</div>
						<div class="form-group d-flex justify-content-between">
							<div>
								<label for="seSouvenir">Se souvenir de moi :</label> <input
									type="checkbox" id="seSouvenir" name="seSouvenir"
									<%if (seSouvenir == true) {%> checked <%}%>>
							</div>
							<a class="float-right" href="passePerdu">Mot de passe oublié</a>
						</div>
						<div class="d-grid mt-3 gap-2">
							<button type="submit" class="btn btn-primary">Se
								connecter</button>
							<a class="btn btn-primary" href="register">S'inscrire</a>
						</div>
						<c:choose>
							<c:when test="${not empty requestScope.erreur}">
								<strong class="erreur"><c:out
										value="${requestScope.erreur}" /></strong>
							</c:when>
						</c:choose>
					</form>
				</div>
			</div>
		</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>