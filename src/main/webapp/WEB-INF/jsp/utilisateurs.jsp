<%@ include file="/WEB-INF/jsp/head/head.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
	<title>ÉcoEnchères - Liste des utilisateurs</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp" %>
	<main>
	
	<c:choose>
		<c:when test="${sessionScope.isConnected.administrateur eq true}">
		<h1>Liste des utilisateurs</h1>
		
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

		</c:when>
		<c:otherwise>
			<span class="message">Vous n'avez pas accès à cette ressource</span>
			<a href="accueil">Accueil</a>
		</c:otherwise>
	</c:choose>
	<c:choose>
			<c:when test="${not empty sessionScope.isConnected}">
			<div class="container mt-5">
				<table class="table table-sm table-hover align-middle" border="1">
					<tr class="table-primary">
						<th scope="col">#</th>
						<th scope="col">Pseudo</th>
						<th scope="col">Nom</th>
						<th scope="col">Prénom</th>
						<th scope="col">Email</th>
						<th scope="col">Téléphone</th>
						<th scope="col">Adresse</th>
						<th scope="col">Crédits</th>
						<th scope="col">Admin</th>
						<th scope="col">État</th>
						<th>X</th>
					</tr>
					<tr>
						<td>
							<a class="bi bi-link-45deg" href="<%=request.getContextPath()%>/profil?id=${sessionScope.isConnected.noUtilisateur}">${sessionScope.isConnected.noUtilisateur}</a>	 
						</td>
						<td>${sessionScope.isConnected.pseudo}</td>
						<td>${sessionScope.isConnected.nom}</td>
						<td>${sessionScope.isConnected.prenom}</td>
						<td>${sessionScope.isConnected.email}</td>
						<td>${sessionScope.isConnected.telephone}</td>
						<td>${sessionScope.isConnected.rue},</br>${sessionScope.isConnected.codePostal} ${sessionScope.isConnected.ville}</td>
						<td>${sessionScope.isConnected.credit}</td>
						<td>
						<c:choose>
							<c:when test="${sessionScope.isConnected.administrateur eq true}">
								<img src="${pageContext.request.contextPath}/img/administrateur.png" width="20" height="20" alt="icone administrateur">
							</c:when>
						</c:choose>
						</td>
						<td></td>
					</tr>
					<c:forEach var="u" items="${listeUtilisateurs}">
						<c:if test="${u.noUtilisateur != sessionScope.isConnected.noUtilisateur}">
							<tr>
								<td>
									<a class="bi bi-link-45deg" href="<%=request.getContextPath()%>/profil?id=${u.noUtilisateur}">${u.noUtilisateur}</a>
								 </td>
								<td>${u.pseudo}</td>
								<td>${u.nom}</td>
								<td>${u.prenom}</td>
								<td>${u.email}</td>
								<td>${u.telephone}</td>
								<td>${u.rue}, </br>${u.codePostal} ${u.ville}</td>
								<td>${u.credit}</td>
								<td>
								<c:choose>
									<c:when test="${u.administrateur eq true}">
										<img src="${pageContext.request.contextPath}/img/administrateur.png" width="20" height="20" alt="icone administrateur">
									</c:when>
								</c:choose>
								</td>
								<td>
								<c:if test="${not u.administrateur}">
									<div class="form-check form-switch">
										<form id="checkActif_${u.noUtilisateur}" action="<%=request.getContextPath()%>/actif?id=${u.noUtilisateur}" method="post" onclick="return confirm('Êtes-vous sûr de vouloir désactiver ce compte ?');">
											<input data-user-id="${u.noUtilisateur}" class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" 
											${u.actif eq true ? 'checked' : '' }
											onchange="submitForm(this, ${u.noUtilisateur})">
										</form>
									</div>
								</c:if>
								<script>
									function submitForm(checkbox,userId) {
										const form = document.querySelector(`#checkActif_`+userId);
										form.submit();
									}
								</script>
								</td>
								<c:choose>
									<c:when test="${not u.administrateur}">
										<td>
											<a href="${pageContext.request.contextPath}/supprimer?id=${u.noUtilisateur}" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce compte ?');">
												<img class="delete-icon" src="${pageContext.request.contextPath}/img/bouton-supprimer.png" width="20" height="20" alt="icone supprimer">
											</a>
										</td>
									</c:when>
									<c:otherwise>
										<td></td>
									</c:otherwise>
								</c:choose>
								
							</tr>
							<script>
								function toggleActif(u.noUtilisateur) {
								const checkbox = document.getElementById(`actifCheckbox_${u.noUtilisateur}`);
								const form = document.getElementById(`actifForm_${u.noUtilisateur}`);

								// Mettre à jour la valeur du champ actif dans le formulaire en fonction de l'état de la checkbox
								form.elements["actif"].value = checkbox.checked ? 1 : 0;

								// Soumettre automatiquement le formulaire lorsque la checkbox est cochée ou décochée
								form.submit();
								}
							</script>
						</c:if>
					</c:forEach>
				</table>
				</div>
			</c:when>
		</c:choose>

	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp" %>	

</body>
</html>