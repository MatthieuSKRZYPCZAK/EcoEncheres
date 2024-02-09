<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<nav class="navbar fixed-top navbar-expand-lg">
		<div class="effect-big">
			<a class="navbar-brand logo-title" href="${pageContext.request.contextPath}/accueil">
				<img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo �coEnch�res" width="50" height="50" class="d-inline-block align-text-mid">
					<span>�coEnch�res</span>
			</a>
		</div>
		<c:choose>
			<c:when test="${not empty sessionScope.isConnected}">
				<div class="collapse navbar-collapse" id="navbarText">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<c:choose>
						<c:when test="${sessionScope.isConnected.administrateur eq true}">
						<li class="nav-item dropdown">
						<button class="btn  dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
							  admin
						</button>
						<ul class="dropdown-menu dropdown-menu-dark">
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/utilisateurs">Liste utilisateurs</a></li>
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/encheres">Liste des ventes</a></li>
							<li><a class="dropdown-item" href="<%=request.getContextPath()%>/categorie">Liste des cat�gories</a></li>
						</ul>
					</li>
						
						</c:when>
					</c:choose>
					
					<li class="nav-item">
						<span class="nav-link effect-big">Mes cr�dits ${sessionScope.isConnected.credit} <img src="${pageContext.request.contextPath}/img/credits.png" alt="Logo cr�dits" width="20" height="20" class="d-inline-block align-text-mid"></span>
					</li>
					<li class="nav-item">
						<a class="nav-link effect-nav" aria-current="page" href="accueil">Liste des ench�res</a>
					</li>
					<li class="nav-item">
						<a class="nav-link effect-nav" href="vendre">Vendre un article</a>
					</li>
					<li class="nav-item">
						<a class="nav-link effect-nav" href="<%=request.getContextPath()%>/mesAchats?id=${sessionScope.isConnected.noUtilisateur}">Mes achats</a>
					</li>
					<li class="nav-item">
						<a class="nav-link effect-nav" href="<%=request.getContextPath()%>/mesVentes?id=${sessionScope.isConnected.noUtilisateur}">Mes ventes</a>
					</li>
				</ul>
				
				</div>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item">
						<a class="nav-link effect-nav" href="<%=request.getContextPath()%>/profil?id=${sessionScope.isConnected.noUtilisateur}">Mon profil : ${sessionScope.isConnected.pseudo}</a>
					</li>
					<li class="nav-item">
						<a class="nav-link effect-nav" href="<%=request.getContextPath()%>/logout">Se d�connecter</a>
					</li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item">
						<a class="nav-link effect-nav" href="<%=request.getContextPath()%>/register">S'inscrire</a>
					</li>
					<li class="nav-item">

					</li>
					<li class="nav-item">
						<a class="nav-link effect-nav" href="<%=request.getContextPath()%>/login">Se connecter</a>
					</li>
				</ul>
			</c:otherwise>
		</c:choose>
	</nav>
</header>