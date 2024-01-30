<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<a class="navbar-brand logo-title" href="${pageContext.request.contextPath}/accueil">
				<img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo ÉcoEnchères" width="50" height="50" class="d-inline-block align-text-mid">
					<span>ÉcoEnchères</span>
			</a>
		</div>
		<c:choose>
					<c:when test="${not empty sessionScope.userConnected}">
						<ul class="navbar-nav ms-auto">
							<li class="nav-item">
								<a class="nav-link" href="<%=request.getContextPath()%>/profil?id=${sessionScope.userConnected.id}">Vous êtes connecté ${sessionScope.userConnected.identifiant}</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="<%=request.getContextPath()%>/logout">Se déconnecter</a>
							</li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="navbar-nav ms-auto">
							<li class="nav-item">
								<a class="nav-link register-login" href="<%=request.getContextPath()%>/register">Inscription</a>
							</li>
							<li class="nav-item">

							</li>
							<li class="nav-item">
								<a class="nav-link register-login" href="<%=request.getContextPath()%>/login">Connection</a>
							</li>
						</ul>
					</c:otherwise>
			</c:choose>
	</nav>
</header>