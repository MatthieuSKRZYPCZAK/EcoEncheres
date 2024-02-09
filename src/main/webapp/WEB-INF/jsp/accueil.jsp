<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<title>ÉcoEnchères - Accueil</title>
</head>
<script>
	function gestionCheckBoxes() {
		var optionSelectionnee = document
				.querySelector('input[name="option"]:checked').value;
		var checkboxes = document.querySelectorAll('input[type="checkbox"]');

		if (optionSelectionnee === 'achats') {
			checkboxes.forEach(function(checkbox) {
				if (checkbox.name === 'ventesEnCours'
						|| checkbox.name === 'venteNonDebutees'
						|| checkbox.name === 'ventesTerminees') {
					checkbox.disabled = true;
					checkbox.checked = false; // Désélectionne la case si elle était cochée
				} else {
					checkbox.disabled = false;
				}
			});
		} else if (optionSelectionnee === 'ventes') {
			checkboxes.forEach(function(checkbox) {
				if (checkbox.name === 'encheresOuvertes'
						|| checkbox.name === 'encheresEnCours'
						|| checkbox.name === 'mesEncheresWin') {
					checkbox.disabled = true;
					checkbox.checked = false; // Désélectionne la case si elle était cochée
				} else {
					checkbox.disabled = false;
				}
			});
		}
	}
</script>
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
		<div class="container mt-2">
			<div class="row justify-content-center text-align-center">
				<h1>Liste des enchères</h1>
				<c:if test="${empty sessionScope.isConnected}">
					<div
						class="alert alert-info alert-dismissible fade show d-flex align-items-center"
						role="alert">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							fill="currentColor"
							class="bi bi-info-circle-fill flex-shrink-0 me-2"
							viewBox="0 0 16 16">
					<path
								d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z" />
				</svg>
						<div>
							Bienvenue ! Pour accéder aux détails des articles, ainsi qu'à la
							possibilité d'acheter ou de vendre un article, il est nécessaire
							de posséder un compte. <a href="register" class="alert-link">S'inscrire</a>
							- <a href="login" class="alert-link">S'identifier</a>
						</div>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
				</c:if>
				<div id="accordion">
					<div class="card">
						<div class="card-header" id="headingOne">
							<h5 class="mb-0">Filtres :</h5>
						</div>
						<div id="collapseOne" class="collapse show"
							aria-labelledby="headingOne" data-parent="#accordion">
							<div class="card-body">
								<form id="search" class="form-inline" action="accueil"
									method="post">
									<div class="row justify-content-center">
										<div class="col-5 mt-1">
											<div class="input-group-append">
												<input class="form-control mr-sm-2" type="text"
													placeholder="Le nom de l'article contient"
													value="${rechercheTape}" aria-label="Recherche"
													id="recherche" name="recherche">
											</div>
										</div>
										<div class="col-1 mt-1">
											<div class="input-group">
												<button class="btn btn-outline-success my-2 my-sm-0"
													type="submit">Rechercher</button>
											</div>
										</div>
									</div>
									<div class="row justify-content-center">
										<div class="col-6 mt-3 d-none d-md-table-cell">
											<div class="input-group">
												<label class="input-group-text" for="inputGroupSelect01">Catégorie
												</label> <select class="form-control" name="listCategorie"
													id="listCategorie">
													<c:choose>
														<c:when test="${not empty selectedCategorie}">
															<option value="${selectedCategorie.noCategorie}" selected>${selectedCategorie.libelle}</option>
														</c:when>
														<c:otherwise>
															<option value="toutes" selected>Toutes</option>
														</c:otherwise>
													</c:choose>
													<option value="toutes">Toutes</option>
													<c:forEach var="categorie" items="${listCategorie}">
														<option value="${categorie.noCategorie}"
															id="listCategorie">${categorie.libelle}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<c:choose>
										<c:when test="${not empty sessionScope.isConnected}">
											<div
												class="row justify-content-center ">
												<div class="col-6 mt-3">
													<div class="input-group d-flex justify-content-center">
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="radio"
																name="option" id="achats" value="achats" checked
																onclick="gestionCheckBoxes()"> <label
																class="form-check-label" for="achats"> Achats </label>
														</div>
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="radio"
																name="option" id="ventes" value="ventes"
																onclick="gestionCheckBoxes()"> <label
																class="form-check-label" for="ventes"> Mes
																ventes </label>
														</div>
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="checkbox"
																name="encheresOuvertes" id="encheresOuvertes"> <label
																class="form-check-label" for="encheresOuvertes">
																enchères ouvertes </label>
														</div>
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="checkbox"
																name="ventesEnCours" id="ventesEnCours" disabled>
															<label class="form-check-label" for="ventesEnCours">
																mes ventes en cours </label>
														</div>
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="checkbox"
																name="encheresEnCours" id="encheresEnCours"> <label
																class="form-check-label" for="encheresEnCours">
																mes enchères en cours </label>
														</div>
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="checkbox"
																name="venteNonDebutees" id="venteNonDebutees" disabled>
															<label class="form-check-label" for="venteNonDebutees">
																ventes non débutées </label>
														</div>
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="checkbox"
																name="mesEncheresWin" id="mesEncheresWin"> <label
																class="form-check-label" for="mesEncheresWin">
																mes enchères remportées </label>
														</div>
														<div class="form-check col-6 d-none d-md-table-cell">
															<input class="form-check-input" type="checkbox"
																name="ventesTerminees" id="ventesTerminees" disabled>
															<label class="form-check-label" for="ventesTerminees">
																ventes terminées </label>
														</div>
													</div>
												</div>
											</div>
										</c:when>
									</c:choose>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div
				class="row row-cols-1 row-cols-xs-2 row-cols-sm-2 row-cols-lg-4 g-3 mt-3">
				<c:choose>
					<c:when test="${listArticle.size() > 0}">
						<c:forEach var="article" items="${listArticle}">
							<div class="col">
								<div class="card h-100 shadow-sm">
									<div class="article-img">
										<img
											src="${pageContext.request.contextPath}/uploads/${article.image}"
											class="" alt="${article.image}">
									</div>
									<div class="card-body">
										<div class="clearfix mb-3">
											<span class="float-start badge rounded-pill bg-success">
												<c:set var="enchereTrouvee" value="false" /> <c:forEach
													var="enchere" items="${encheres}">
													<c:if
														test="${not empty enchere && enchere.article.noArticle == article.noArticle}">
														${enchere.montantEnchere}
														<c:set var="enchereTrouvee" value="true" />
													</c:if>
												</c:forEach> <c:if test="${not enchereTrouvee}">
														${article.prixInitial}
													</c:if> <img
												src="${pageContext.request.contextPath}/img/credits.png"
												alt="Logo crédits" width="20" height="20"
												class="d-inline-block align-text-mid">
											</span> <span class="float-end badge rounded-pill bg-primary">${article.categorie.libelle}</span>
										</div>
										<h5 class="card-title">${article.nomArticle}</h5>
										<p>${article.description}</p>
										<p>
											Fin de l'enchère :<br>
											<br> Le
											<fmt:formatDate type="date"
												value="${article.dateFinEncheres}" />
											à
											<fmt:formatDate pattern="HH"
												value="${article.dateFinEncheres}" />
											h
											<fmt:formatDate pattern="mm"
												value="${article.dateFinEncheres}" />
										</p>
										<c:if test="${not empty sessionScope.isConnected}">
											<div class="text-center my-4">
												<a
													href="${pageContext.request.contextPath}/article?id=${article.noArticle}"
													class="btn btn-warning">voir l'offre</a>
											</div>
										</c:if>
										<div class="clearfix mb-1">
											<span class="float-end"><i class="fas fa-plus">Vendeur
													: ${article.utilisateur.pseudo}</i></span>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:when>
				</c:choose>
			</div>
		</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>