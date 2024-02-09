<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>ÉcoEnchères - Mon profil</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp"%>
	<main>
		<c:choose>
			<c:when test="${not empty sessionScope.isConnected}">
				<c:choose>
					<c:when test="${moi==true}">
						<div class="container mt-5">
							<div class="row justify-content-center">
								<div class="col-12 text-center">
									<h1>Mon profil</h1>
								</div>
								<p class="credit text-center display-6 effect-big">
									Mes crédit : ${utilisateur.credit} <img
										src="${pageContext.request.contextPath}/img/credits.png"
										alt="Logo crédits" width="50" height="50"
										class="d-inline-block align-text-mid">
								</p>
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
					</c:when>
					<c:otherwise>
						<div class="container mt-5">
							<div class="row justify-content-center">
								<div class="col-12 text-center">
									<h1>Profil de ${utilisateur.pseudo}</h1>
								</div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when
						test="${sessionScope.isConnected.actif eq false && sessionScope.isConnected.noUtilisateur eq utilisateur.noUtilisateur}">
						<div class="container mt-5">
							<div class="row justify-content-center">
								<div class="col-12 text-center">
									<strong class="erreur">Votre compte a été désactivé
										par un administrateur</strong>
									<p>Vous ne pouvez plus vendre ou acheter d'article</p>
								</div>
							</div>
						</div>
					</c:when>
				</c:choose>

				<form id="profil"
					action="${pageContext.request.contextPath}/profil?id=${utilisateur.noUtilisateur}"
					method="post"
					onsubmit="return confirm('Êtes-vous sûr de vouloir modifier vos informations ?');">
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="pseudo"><i
									class="bi bi-person-circle"></i></span> <input type="text"
									pattern="^[a-zA-Z0-9]{3,30}$" class="form-control"
									name="pseudo" aria-label="Pseudo" aria-describedby="Pseudo"
									value="${utilisateur.pseudo}" placeholder="Pseudo" required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="nom"><i
									class="bi bi-person-fill"></i></span> <input type="text"
									class="form-control" name="nom" aria-label="nom"
									aria-describedby="nom" value="${utilisateur.nom}"
									placeholder="Nom" required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="prenom"><i
									class="bi bi-person-fill"></i></span> <input type="text"
									class="form-control" name="prenom" aria-label="prenom"
									aria-describedby="prenom" value="${utilisateur.prenom}"
									placeholder="Prénom" required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="email"><i
									class="bi bi-envelope-at-fill"></i></span> <input type="email"
									class="form-control" name="email" aria-label="email"
									aria-describedby="email" value="${utilisateur.email}"
									placeholder="Email" required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="telephone"><i
									class="bi bi-telephone-fill"></i></span> <input type="tel"
									class="form-control" name="telephone" aria-label="telephone"
									aria-describedby="telephone" value="${utilisateur.telephone}"
									minlength="10" maxlength="10" placeholder="Numéro de téléphone"
									required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="rue"><i
									class="bi bi-house-fill"></i></span> <input type="text"
									class="form-control" name="rue" aria-label="rue"
									aria-describedby="rue" value="${utilisateur.rue}"
									placeholder="Adresse" required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="codePostal"><i
									class="bi bi-building-fill"></i></span> <input type="number"
									class="form-control" name="codePostal" aria-label="codePostal"
									aria-describedby="codePostal" value="${utilisateur.codePostal}"
									minlength="5" maxlength="5" placeholder="Code postal" required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="ville"><i
									class="bi bi-building-fill"></i></span> <input type="text"
									class="form-control" name="ville" aria-label="ville"
									aria-describedby="ville" value="${utilisateur.ville}"
									placeholder="Ville" required>
							</div>
						</div>
					</div>
					<c:choose>
						<c:when test="${moi==true}">
							<div class="row justify-content-center">
								<div class="col-4 mt-3">
									<div class="input-group">
										<span class="input-group-text" id="motDePasse"><i
											class="bi bi-key-fill"></i></span> <input type="password"
											class="form-control"
											pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%^&+=!])(?!.*\s).{8,}$"
											name="nouveauMotDePasse" aria-label="nouveauMotDePasse"
											aria-describedby="nouveauMotDePasse" value="${motDePasse}"
											placeholder="Nouveau mot de passe"
											title="Doit contenir au moins 8 caractères, dont au moins une majuscule, une minuscule, un chiffre et un caractère spécial">
									</div>
								</div>
								<div class="col-4 mt-3">
									<div class="input-group">
										<span class="input-group-text" id="confirmation"><i
											class="bi bi-check2-all"></i></span> <input type="password"
											class="form-control" name="confirmationMotDePasse"
											aria-label="confirmationMotDePasse"
											aria-describedby="confirmationMotDePasse"
											value="${confirmation}"
											placeholder="Confirmation du mot de passe">
									</div>
								</div>
							</div>
							<div class="row justify-content-center">
								<div class="col-4 mt-3">
									<div class="input-group">
										<span class="input-group-text" id="motDePasse"><i
											class="bi bi-key-fill"></i></span> <input type="password"
											class="form-control" name="motDePasse"
											aria-label="motDePasse" aria-describedby="motDePasse"
											placeholder="Mot de passe actuel" required>
									</div>
								</div>
							</div>
							<div class="row justify-content-center">
								<div class="col-3 d-grid mt-3">
									<input class="btn btn-primary" type="submit" value="Modifier"
										id="submit">
								</div>
							</div>
				</form>
				<div class="row justify-content-center">
					<div class="col-3 d-grid mt-3">
						<input class="btn btn-danger" data-bs-toggle="modal"
							data-bs-target="#deleteModal" value="Supprimer mon compte"
							id="delete-account">
					</div>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="deleteModal" tabindex="-1"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<form id="motDePasse"
								action="${pageContext.request.contextPath}/supprimer?id=${sessionScope.isConnected.noUtilisateur}"
								method="post">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Suppression
										de mon compte</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">
									<strong class="erreur"> Êtes-vous sûr de vouloir
										supprimer votre compte ? </strong>
									<div class="row justify-content-center">
										<div class="col-8 mt-3">

											<div class="input-group">
												<span class="input-group-text" id="motDePasse"><i
													class="bi bi-key-fill"></i></span> <input type="password"
													class="form-control" name="motDePasse"
													aria-label="motDePasse" aria-describedby="motDePasse"
													placeholder="Mot de passe actuel" required>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">Annuler</button>
									<button class="btn btn-danger" type="submit">Je
										confirme</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</c:when>
		</c:choose>
		</c:when>
		<c:otherwise>
			<strong class="erreur">Vous n'avez pas accès à cette
				ressource</strong>
		</c:otherwise>
		</c:choose>
		</div>
		</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>