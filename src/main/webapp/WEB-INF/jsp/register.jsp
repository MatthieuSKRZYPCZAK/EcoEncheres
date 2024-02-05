<%@ include file="/WEB-INF/jsp/head/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>�coEnch�res - Inscription</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp"%>
	<main>
		<div class="container mt-5">
			<div class="row justify-content-center">
				<div class="col-12 text-center">
					<h1>Inscription</h1>
				</div>
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger" role="alert">
						<c:out value="${errorMessage}"></c:out>
					</div>
				</c:if>
				<form action="register" method="post">
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="pseudo"><i
									class="bi bi-person-circle"></i></span> <input type="text"
									pattern="^[a-zA-Z0-9]{3,30}$" class="form-control"
									name="pseudo" aria-label="Pseudo" aria-describedby="Pseudo"
									value="${pseudo}" placeholder="Pseudo"
									title="Le pseudo doit �tre alphanum�rique et ne doit pas contenir d'espace, 3 caract�res minimum"
									required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="nom"><i
									class="bi bi-person-fill"></i></span> <input type="text"
									class="form-control" name="nom" aria-label="nom"
									aria-describedby="nom" value="${nom}" placeholder="Nom"
									required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="prenom"><i
									class="bi bi-person-fill"></i></span> <input type="text"
									class="form-control" name="prenom" aria-label="prenom"
									aria-describedby="prenom" value="${prenom}"
									placeholder="Pr�nom" required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="email"><i
									class="bi bi-envelope-at-fill"></i></span> <input type="email"
									class="form-control" name="email" aria-label="email"
									aria-describedby="email" value="${email}" placeholder="Email"
									required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="telephone"><i
									class="bi bi-telephone-fill"></i></span> <input type="tel"
									class="form-control" name="telephone" aria-label="telephone"
									aria-describedby="telephone" value="${telephone}"
									minlength="10" maxlength="10" placeholder="Num�ro de t�l�phone"
									required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="rue"><i
									class="bi bi-house-fill"></i></span> <input type="text"
									class="form-control" name="rue" aria-label="rue"
									aria-describedby="rue" value="${rue}" placeholder="Adresse"
									required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="codePostal"><i
									class="bi bi-building-fill"></i></span> <input type="number"
									class="form-control" name="codePostal" aria-label="codePostal"
									aria-describedby="codePostal" value="${codePostal}"
									minlength="5" maxlength="5" placeholder="Code postal" required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="ville"><i
									class="bi bi-building-fill"></i></span> <input type="text"
									class="form-control" name="ville" aria-label="ville"
									aria-describedby="ville" value="${ville}" placeholder="Ville"
									required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="motDePasse"><i
									class="bi bi-key-fill"></i></span> <input type="password"
									class="form-control"
									pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%^&+=!])(?!.*\s).{8,}$"
									name="motDePasse" aria-label="motDePasse"
									aria-describedby="motDePasse" value="${motDePasse}"
									placeholder="Mot de passe"
									title="Doit contenir au moins 8 caract�res, dont au moins une majuscule, une minuscule, un chiffre et un caract�re sp�cial"
									required>
							</div>
						</div>
						<div class="col-4 mt-3">
							<div class="input-group">
								<span class="input-group-text" id="confirmation"><i
									class="bi bi-check2-all"></i></span> <input type="password"
									class="form-control" name="confirmationMotDePasse"
									aria-label="confirmation" aria-describedby="confirmation"
									value="${confirmation}"
									placeholder="Confirmation du mot de passe" required>
							</div>
						</div>
					</div>
					<div class="row justify-content-center">
						<div class="col-3 d-grid mt-3">
							<input class="btn btn-primary" type="submit" value="Cr�er"
								id="submit">
						</div>
						<div class="col-3 d-grid mt-3">
							<a class="btn btn-primary" href="accueil">Annuler</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp"%>
</body>
</html>