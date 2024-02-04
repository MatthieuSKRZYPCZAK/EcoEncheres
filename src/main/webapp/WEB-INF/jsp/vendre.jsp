<%@ include file="/WEB-INF/jsp/head/head.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

	<title>ÉcoEnchères - Vendre un article</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp" %>
	<main>
	<%-- Vérifie si l'utilisateur est connecté et si son compte est actif --%>
	<c:choose>
		<c:when test="${not empty sessionScope.isConnected && sessionScope.isConnected.actif}">
			<c:choose>
				<c:when test="${not empty requestScope.erreur}">
					<div class="container mt-1">
						<div class="row justify-content-center text-center">
							<strong class="erreur"><c:out value="${requestScope.erreur}" /></strong>
						</div>
					</div>
				</c:when>
				<c:when test="${not empty requestScope.successMessage}">
					<div class="container mt-1">
						<div class="row justify-content-center text-center">
							<strong class="succes"><c:out value="${requestScope.successMessage}" /></strong>
						</div>
					</div>
				</c:when>
			</c:choose>
			<%-- Formulaire vente article --%>
			<div class="container mt-1">
				<div class="row justify-content-center">
					<div class="col-12 text-center mb-0">
						<h1 class="mb-0">Nouvelle vente</h1>
					</div>
						<c:choose>
							<c:when test="${not empty sessionScope.erreur}">
							<div class="message">
								<strong class="erreur"><c:out value="${sessionScope.erreur}" /></strong>
								<c:remove var="erreur" scope="session" />
							</div>
							</c:when>
							<c:when test="${not empty sessionScope.successMessage}">
							<div class="message">
								<strong class="succes"><c:out value="${sessionScope.successMessage}" /></strong>
								<c:remove var="successMessage" scope="session" />
							</div>
							</c:when>
							</c:choose>
							<div class="row justify-content-center d-flex m-5">
								<div class="col-md-5">
									<form action="vendre" method="post" enctype="multipart/form-data">

										<div class="input-group">
											<span class="input-group-text" id="nomArticle">Nom de l'article</span>
											<input type="text" class="form-control" name="nomArticle" aria-label="Nom article" aria-describedby="Nom article" value="${nomArticle}"  required>
										</div>	
										<div class="input-group mt-3">
											<span class="input-group-text" id="description">Description</span>
											<textarea class="form-control" name="description" aria-label="Description" aria-describedby="Description" value="${description}" required></textarea>
										</div>	
										<div class="form-group mt-3">
											<label for="listCategorie">Catégorie</label>
											<select class="form-control" name="listCategorie" id="listCategorie">
												<c:forEach var="categorie" items="${listCategorie}">
													<option value="${categorie.noCategorie}" id="listCategorie">${categorie.libelle}</option>
												</c:forEach>
											</select>
										</div>	
										<div class="form-group mt-3">
											<label for="image">Photo de l'article</label>
											<input type="file" class="form-control" id="image" name="image" accept="image/png, image/jpeg" required>
										</div>
										<div class="input-group mt-3">
											<span class="input-group-text" id="prixInitial">Mise à prix</span>
											<input type="number" class="form-control" name="prixInitial"  aria-label="Prix initial" aria-describedby="Prix initial" value="${prixInitial}" minlength="1" maxlength="5" required>
										</div>	
										<div class="form-group mt-3">
											<label for="dateDebutEncheres">Début de l'enchère:</label>
											<input type="datetime-local" class="form-control" name="dateDebutEncheres" id="dateDebutEncheres" value="${dateDebutEncheres}" required>
										</div>
										<div class="form-group mt-3">
											<label for="dateFinEncheres">Fin de l'enchère:</label>
											<input type="datetime-local" class="form-control" name="dateFinEncheres" id="dateFinEncheres" value="${dateFinEncheres}" required>
										</div>
										<fieldset class="rounded-3 border mt-3 p-3">
										<legend class="float-none w-auto px-3 ">Retrait</legend>
											<div class="input-group mt-3">
												<span class="input-group-text" id="rue">Rue</span>
												<input type="text" class="form-control" name="rue" aria-label="Rue" aria-describedby="Rue" value="${rue}"  required>
											</div>	
											<div class="input-group mt-3">
												<span class="input-group-text" id="codePostal">Code postal</span>
												<input type="number" class="form-control" name="codePostal"  aria-label="codePostal" aria-describedby="codePostal" value="${codePostal}" minlength="5" maxlength="5"  required>
											</div>	
											<div class="input-group mt-3">
												<span class="input-group-text" id="ville">Ville</span>
												<input type="text" class="form-control" name="ville" aria-label="Ville" aria-describedby="Ville" value="${ville}"  required>
											</div>	
										</fieldset>
										<div class="row justify-content-center">
											<div class="col-3 d-grid mt-3">
												<input class="btn btn-primary" type="submit" value="Vendre" id="submit"> 
											</div>
											<div class="col-3 d-grid mt-3">
												<a class="btn btn-primary"  href="accueil">Annuler</a>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>

		</c:when>
		<%-- Si le compte est désactivé affiche un message --%>
		<c:when test="${sessionScope.isConnected.actif eq false}">
			<span class="message">Votre compte est désactivé, vous ne pouvez pas vendre d'article</span>
			<a href="accueil">Accueil</a>
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