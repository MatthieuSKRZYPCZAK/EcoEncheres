<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<title>ÉcoEnchères - ${article.nomArticle}</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp"%>
	<main>
		<div class="message">
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
		<c:choose>
			<c:when test="${not empty sessionScope.isConnected}">
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
				<div class="container-md px-5 mx-0">
					<div class="title text-center">
						<h1 class="text-uppercase my-5">Détail vente</h1>
						<c:if test="${article.etatVente == 'créé'}">
							<h2>
								L'enchère commencera le
								<fmt:formatDate type="both" value="${article.dateDebutEncheres}" />
							</h2>
						</c:if>
						<c:if
							test="${article.etatVente == 'terminé' || article.etatVente == 'retiré'}">
							<c:choose>
								<c:when
									test="${enchere.utilisateur.noUtilisateur == sessionScope.isConnected.noUtilisateur}">
									<c:choose>
										<c:when test="${article.etatVente == 'retiré'}">
											<div class="container mt-5">
												<div class="alert alert-success" role="alert">
													<h2 class="text-center">Vous avez remporté la vente -
														l'article a été retiré</h2>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="container mt-5">
												<div class="alert alert-success" role="alert">
													<h2 class="text-center">Vous avez remporté la vente</h2>
													<a href="retrait?id=${article.noArticle}" id="lien"
														class="btn btn-outline-success mt-3">Confirmer le
														retrait</a>
												</div>
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<div class="container mt-5">
										<div class="alert alert-secondary" role="alert">
											<c:choose>
												<c:when test="${not empty enchere.utilisateur.pseudo}">
													<h2>${enchere.utilisateur.pseudo}aremporté l'enchère</h2>
												</c:when>
												<c:otherwise>
													<h2>Enchère terminée sans offre.</h2>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<div class="row">
						<div class="col-md-1"></div>
						<div class="col-md-5 photo-container">
							<img class=" rounded-start"
								src="${pageContext.request.contextPath}/uploads/${article.image}"
								alt="${article.nomArticle}">
						</div>
						<c:choose>
							<c:when
								test="${article.etatVente == 'créé' && (sessionScope.isConnected.noUtilisateur == article.utilisateur.noUtilisateur)}">
								<div class="col-md-6">
									<div class="card-body">
										<form action="article" method="post"
											enctype="multipart/form-data">
											<input type="hidden" name="noArticle"
												value="${article.noArticle}">
											<div class="input-group">
												<span class="input-group-text" id="nomArticle">Nom de
													l'article</span> <input type="text" class="form-control"
													name="nomArticle" aria-label="Nom article"
													aria-describedby="Nom article"
													value="${article.nomArticle}" required>
											</div>
											<div class="input-group mt-3">
												<span class="input-group-text" id="description">Description</span>
												<textarea class="form-control" name="description"
													aria-label="Description" aria-describedby="Description"
													required>${article.description}</textarea>
											</div>
											<div class="form-group mt-3">
												<label for="listCategorie">Catégorie</label> <select
													class="form-control" name="listCategorie"
													id="listCategorie">
													<c:forEach var="categorie" items="${listCategorie}">
														<option value="${categorie.noCategorie}"
															id="listCategorie">${categorie.libelle}</option>
													</c:forEach>
												</select>
											</div>
											<div class="form-group mt-3">
												<label for="image">Photo de l'article</label> <input
													type="file" class="form-control" id="image" name="image"
													accept="image/png, image/jpeg">
											</div>
											<div class="input-group mt-3">
												<span class="input-group-text" id="prixInitial">Mise
													à prix</span> <input type="number" class="form-control"
													name="prixInitial" aria-label="Prix initial"
													aria-describedby="Prix initial"
													value="${article.prixInitial}" minlength="1" maxlength="5"
													min="0" max="50000" required>
											</div>
											<div class="form-group mt-3">
												<label for="dateDebutEncheres">Début de l'enchère:</label> <input
													type="datetime-local" class="form-control"
													name="dateDebutEncheres" id="dateDebutEncheres"
													value="${article.dateDebutEncheres}" required>
											</div>
											<div class="form-group mt-3">
												<label for="dateFinEncheres">Fin de l'enchère:</label> <input
													type="datetime-local" class="form-control"
													name="dateFinEncheres" id="dateFinEncheres"
													value="${article.dateFinEncheres}" required>
											</div>
											<fieldset class="rounded-3 border mt-3 p-3">
												<legend class="float-none w-auto px-3 ">Retrait</legend>
												<div class="input-group mt-3">
													<span class="input-group-text" id="rue">Rue</span> <input
														type="text" class="form-control" name="rue"
														aria-label="Rue" aria-describedby="Rue"
														value="${retrait.rue}" required>
												</div>
												<div class="input-group mt-3">
													<span class="input-group-text" id="codePostal">Code
														postal</span> <input type="number" class="form-control"
														name="codePostal" aria-label="codePostal"
														aria-describedby="codePostal"
														value="${retrait.codePostal}" minlength="5" maxlength="5"
														required>
												</div>
												<div class="input-group mt-3">
													<span class="input-group-text" id="ville">Ville</span> <input
														type="text" class="form-control" name="ville"
														aria-label="Ville" aria-describedby="Ville"
														value="${retrait.ville}" required>
												</div>
											</fieldset>
											<div class="row justify-content-center">
												<div class="col-3 d-grid mt-3">
													<input class="btn btn-primary" type="submit"
														value="Modifier la vente" id="submit">
												</div>
												<div class="col-3 d-grid mt-3">
													<a class="btn btn-danger"
														href="supprimerVente?id=${article.noArticle}">Annuler
														la vente</a>
												</div>
											</div>
										</form>
							</c:when>
							<c:otherwise>
								<div class="col-md-6">
									<div class="card-body">
										<h3>${article.nomArticle}</h3>
										<p class="card-text">
											<b>Description :</b> ${article.description}
										</p>
										<p class="card-text">
											<b>Catétorie :</b> ${article.categorie.libelle}
										</p>
										<p class="card-text">
											<b>Meilleur offre : <c:choose>
													<c:when test="${empty enchere.montantEnchere}">
										pas d'offre pour le moment
									</c:when>
													<c:otherwise>
										${enchere.montantEnchere} <img
															src='${pageContext.request.contextPath}/img/credits.png'
															alt='Logo crédits' width='20' height='20'
															class='d-inline-block align-text-mid'> par ${enchere.utilisateur.pseudo}
									</c:otherwise>
												</c:choose>
											</b>
										</p>
										<p class="card-text">
											<b>Mise à prix :</b> ${article.prixInitial} <img
												src="${pageContext.request.contextPath}/img/credits.png"
												alt="Logo crédits" width="20" height="20"
												class="d-inline-block align-text-mid">
										</p>
										<p class="card-text">
											<b>Fin de l'enchère :</b> ${article.dateFinEncheres}
										</p>
										<p class="card-text">
											<b>Retrait :</b> ${retrait.rue}<br /> ${retrait.codePostal}
											${retrait.ville}
										</p>
										<p class="card-text">
											<b>Vendeur :</b> <a class="bi bi-link-45deg decoration-none"
												href="<%=request.getContextPath()%>/profil?id=${article.utilisateur.noUtilisateur}">${article.utilisateur.pseudo}</a>
										</p>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when
								test="${sessionScope.isConnected.actif eq true && article.etatVente == 'en cours'}">
								<div class="card">
									<div class="card-body">
										<form action="enchere" method="post">
											<c:set var="montantConditionnel"
												value="${empty enchere ? article.prixInitial : enchere.montantEnchere}" />
											<c:set var="readOnly"
												value="${sessionScope.isConnected.credit < montantConditionnel}" />
											<fieldset
												<c:if test="${readOnly || (sessionScope.isConnected.noUtilisateur == article.utilisateur.noUtilisateur) || (sessionScope.isConnected.noUtilisateur == enchere.utilisateur.noUtilisateur)}">
												disabled
												</c:if>>
												<input type="hidden" name="noArticle"
													value="${article.noArticle}" />
												<p>
													Je possède actuellement :
													${sessionScope.isConnected.credit} <img
														src="${pageContext.request.contextPath}/img/credits.png"
														alt="Logo crédits" width="20" height="20"
														class="d-inline-block align-text-mid">
												</p>
												<c:if
													test="${readOnly && !(sessionScope.isConnected.noUtilisateur == article.utilisateur.noUtilisateur)}">
													<strong class="erreur">Vous ne possédez pas assez
														de <img
														src="${pageContext.request.contextPath}/img/credits.png"
														alt="Logo crédits" width="20" height="20"
														class="d-inline-block align-text-mid"> pour
														enchérir.
													</strong>
												</c:if>
												<div class="row justify-content-center">
													<div class="input-group mt-3 col-1 mt-3">
														<span class="input-group-text" id="prixEnchere"><b>Ma
																proposition :</b></span> <input type="number" class="form-control"
															name="prixEnchere" aria-label="Prix Enchere"
															aria-describedby="Prix Enchere"
															value="${empty enchere.montantEnchere ? article.prixInitial : enchere.montantEnchere + 10}"
															maxlength="5" min="0" max="50000"
															required
												 ${readOnly ? "readonly
															style='background-color: #f2f2f2; color: #666;'" : ''}>
													</div>
													<div class="col-2 mt-3">
														<div class="input-group">
															<button class="btn btn-outline-success my-2 my-sm-0"
																type="submit"
																onsubmit="return confirm('Êtes-vous sûr de vouloir enchérir ?');">Enchérir</button>
														</div>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</c:when>
						</c:choose>
						<c:if
							test="${sessionScope.isConnected.actif eq false && article.etatVente == 'en cours'}">
							<div class="card">
								<div class="card-body">
									<strong class="erreur">Votre compte a été désactivé
										par un administrateur</strong>
									<p>Vous ne pouvez plus vendre ou acheter d'article</p>
								</div>
							</div>
						</c:if>
					</div>
				</div>
				</div>
				</div>
			</c:when>
			<c:otherwise>
				<span class="message">Vous devez vous connecter pour accéder
					au détail de l'article</span>
				<a href="login">S'identifier</a>
			</c:otherwise>
		</c:choose>
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>
<script>
	document.getElementById("lien").addEventListener("click", function(event) {
		if (!confirm("Êtes-vous sûr de vouloir confirmer le retrait ?")) {
			event.preventDefault();
		}
	});
</script>