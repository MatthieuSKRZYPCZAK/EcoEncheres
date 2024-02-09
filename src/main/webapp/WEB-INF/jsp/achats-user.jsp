<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<title>ÉcoEnchères - Mes achats</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp"%>
	<main>
		<c:choose>
			<c:when
				test="${sessionScope.isConnected.noUtilisateur eq utilisateur.noUtilisateur }">
				<h1>Mes achats</h1>
				<c:choose>
					<c:when test="${not empty requestScope.erreur}">
						<div class="container mt-1">
							<div class="row justify-content-center text-center">
								<strong class="erreur"><c:out
										value="${requestScope.erreur}" /></strong>
							</div>
						</div>
					</c:when>
					<c:when test="${not empty requestScope.successMessage}">
						<div class="container mt-1">
							<div class="row justify-content-center text-center">
								<strong class="succes"><c:out
										value="${requestScope.successMessage}" /></strong>
							</div>
						</div>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="row justify-content-center text-center">
					<span class="message">Vous n'avez pas accès à cette
						ressource</span> <a href="accueil">Accueil</a>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="container mt-5">
			<table class="table table-sm table-hover align-middle table-striped"
				border="1">
				<tr class="table-primary">
					<th scope="col">#</th>
					<th scope="col">Nom de l'article</th>
					<th scope="col">Vendeur</th>
					<th scope="col">Prix initial</th>
					<th scope="col">Prix enchère</th>
					<th scope="col" class="d-none d-md-table-cell">Date début</th>
					<th scope="col" class="d-none d-md-table-cell">Date fin</th>
					<th scope="col">Etat vente</th>
					<th scope="col" class="d-none d-md-table-cell">Catégorie</th>
					<th scope="col" class="d-none d-md-table-cell">image</th>
				</tr>
				<c:forEach var="e" items="${listeEncheres}">
					<tr>
						<td><a class="bi bi-link-45deg"
							href="<%=request.getContextPath()%>/article?id=${e.noArticle}">${e.noArticle}</a>
						</td>
						<td>${e.nomArticle}</td>
						<td>${e.utilisateur.pseudo}</td>
						<td>${e.prixInitial}</td>
						<td>${e.prixVente}</td>
						<td class="d-none d-md-table-cell"><fmt:formatDate
								pattern="dd-MM-yyyy HH:mm" value="${e.dateDebutEncheres}" /></td>
						<td class="d-none d-md-table-cell"><fmt:formatDate
								pattern="dd-MM-yyyy HH:mm" value="${e.dateFinEncheres}" /></td>
						<td>${e.etatVente}</td>
						<td class="d-none d-md-table-cell">${e.categorie.libelle}</td>
						<td class="d-none d-md-table-cell">
							<div class="photo-admin">
								<img src="${pageContext.request.contextPath}/uploads/${e.image}"
									width="50px" height="50px" alt="${e.image}">
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>