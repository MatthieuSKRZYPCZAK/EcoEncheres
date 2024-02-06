<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<title>�coEnch�res - ${article.nomArticle}</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp"%>
	<main>
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
						<h1 class="text-uppercase my-5">D�tail vente</h1>
					</div>

					<div class="row">
						<div class="col-md-1"></div>
						<div class="col-md-5 photo-container">
							<img class=" rounded-start"
								src="${pageContext.request.contextPath}/uploads/${article.image}"
								alt="${article.nomArticle}">
						</div>
						<div class="col-md-6">
							<div class="card-body">
								<h3>${article.nomArticle}</h3>
								<p class="card-text">
									<b>Description :</b> ${article.description}
								</p>
								<p class="card-text">
									<b>Cat�torie :</b> ${article.categorie.libelle}
								</p>
								<p class="card-text">
									<b>Meilleur offre :</b> ${empty enchere.montantEnchere ? "pas d'offre pour le moment" : 
							"<img
												src='${pageContext.request.contextPath}/img/credits.png'
												alt='Logo cr�dits' width='20' height='20'
												class='d-inline-block align-text-mid'>" +
							enchere.montantEnchere + " par " + enchere.utilisateur.pseudo}
								</p>
								<p class="card-text">
									<b>Mise � prix :</b> ${article.prixInitial} <img
										src="${pageContext.request.contextPath}/img/credits.png"
										alt="Logo cr�dits" width="20" height="20"
										class="d-inline-block align-text-mid">
								</p>
								<p class="card-text">
									<b>Fin de l'ench�re :</b> ${article.dateFinEncheres}
								</p>
								<p class="card-text">
									<b>Retrait :</b> ${retrait.rue}<br /> ${retrait.codePostal}
									${retrait.ville}
								</p>
								<p class="card-text">
									<b>Vendeur :</b> <a class="bi bi-link-45deg decoration-none"
										href="<%=request.getContextPath()%>/profil?id=${article.utilisateur.noUtilisateur}">${article.utilisateur.pseudo}</a>
								</p>
								<div class="card">
									<div class="card-body">
										<c:choose>
											<c:when test="${sessionScope.isConnected.actif eq true}">
												<form>
													<c:set var="montantConditionnel"
														value="${empty enchere ? article.prixInitial : enchere.montantEnchere}" />
													<c:set var="readOnly"
														value="${sessionScope.isConnected.credit <= montantConditionnel}" />
													<p>
														Je poss�de actuellement :
														${sessionScope.isConnected.credit} <img
															src="${pageContext.request.contextPath}/img/credits.png"
															alt="Logo cr�dits" width="20" height="20"
															class="d-inline-block align-text-mid">
													</p>
													<c:if test="${readOnly}">
														<strong class="erreur">Vous ne poss�dez pas assez
															de <img
															src="${pageContext.request.contextPath}/img/credits.png"
															alt="Logo cr�dits" width="20" height="20"
															class="d-inline-block align-text-mid"> pour
															ench�rir.
														</strong>
													</c:if>
													<div class="input-group mt-3">

														<span class="input-group-text" id="prixInitial"><b>Ma
																proposition :</b></span> <input type="number" class="form-control"
															name="prixInitial" aria-label="Prix initial"
															aria-describedby="Prix initial"
															value="${empty enchere.montantEnchere ? article.prixInitial : enchere.montantEnchere + 10}"
															maxlength="5" min="0" max="50000"
															required
											 ${readOnly ? "readonly
															style='background-color: #f2f2f2; color: #666;'" : ''}>
													</div>
												</form>
											</c:when>
											<c:otherwise>
												<strong class="erreur">Votre compte a �t� d�sactiv�
													par un administrateur</strong>
												<p>Vous ne pouvez plus vendre ou acheter d'article</p>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>


					</div>
				</div>

			</c:when>
			<c:otherwise>
				<span class="message">Vous devez vous connecter pour acc�der
					au d�tail de l'article</span>
				<a href="login">S'identifier</a>
			</c:otherwise>
		</c:choose>
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>