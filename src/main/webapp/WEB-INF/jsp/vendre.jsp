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
		<h1>Vendre un article</h1>
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
			<p>Formulaire</p>
			
			
			
			
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