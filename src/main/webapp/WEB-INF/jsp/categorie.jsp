<%@ include file="/WEB-INF/jsp/head/head.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

	<title>ÉcoEnchères - Catégorie</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp" %>
	<main>
		<c:choose>
		<c:when test="${sessionScope.isConnected.administrateur eq true}">
		<div class="container mt-1">
			<div class="row justify-content-center text-center">
				<div class="col-12 text-center">
					<h1>Catégories</h1>
					<form action="categorie" method="post">
						<div class="row justify-content-center">
							<div class="col-4 mt-1">
								<div class="input-group">
									<span class="input-group-text" id="categorie"><i class="bi bi-basket2"></i></span>
									<input type="tel" class="form-control" name="categorie"  aria-label="categorie" aria-describedby="categorie"  placeholder="nom de catégorie" required>
								</div>	
							</div>
						</div>
						<div class="row justify-content-center">
							<div class="col-3 d-grid mt-1">
								<input class="btn btn-primary" type="submit" value="Créer une catégorie" id="submit"> 
							</div>
						</div>
					</form>
				</div>
				<c:choose>
					<c:when test="${not empty requestScope.erreur}">
						<strong class="erreur"><c:out value="${requestScope.erreur}" /></strong>
					</c:when>
					<c:when test="${not empty requestScope.successMessage}">
						<strong class="succes"><c:out value="${requestScope.successMessage}" /></strong>
					</c:when>
				</c:choose>
			</div>
		</div>
		<div class="row justify-content-center">
		<div class="container mt-5">
				<table class="table table-sm table-hover table-striped align-middle" border="1">
					<tr class="table-primary">
						<th scope="col">#</th>
						<th scope="col">Libelle</th>
						<th scope="col">Modifier</th>
						<th scope="col">Supprimer</th>
					</tr>
			<c:forEach items="${listeCategories}" var="categorie">
					<tr>
					<td>
						${categorie.noCategorie}
					</td>
					<form 	action="<%=request.getContextPath()%>/modifierCategorie?id=${categorie.noCategorie}" method="post" onsubmit="return confirm('Êtes-vous sûr de vouloir modifier cette catégorie ?');">
					<td>
					<div class="col-sm-10">
						<input type="text" class="form-control form-control-sm" name="categorie" aria-label="Categorie" aria-describedby="Categorie" value="${categorie.libelle}" placeholder="${categorie.libelle}" required>
					</div>
					</td>
					<td class="text-center">
						<button class="btn btn-outline-*" type="submit">
							<img class="update-icon" src="${pageContext.request.contextPath}/img/construction.png" width="20" height="20" alt="icone modifier">
						</button>
					</td>
					</form>
					<td class="text-center">
						<a href="${pageContext.request.contextPath}/supprimerCategorie?id=${categorie.noCategorie}" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette catégorie ?');">
							<img class="delete-icon" src="${pageContext.request.contextPath}/img/bouton-supprimer.png" width="20" height="20" alt="icone supprimer">
						</a>
					</td>
					</tr>
					</c:forEach>
				</table>
				</div>
		</div>
		</c:when>
		<c:otherwise>
			<span class="message">Vous n'avez pas accès à cette ressource</span>
			<a href="accueil">Accueil</a>
		</c:otherwise>
	</c:choose>

	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp" %>	
</body>
</html>