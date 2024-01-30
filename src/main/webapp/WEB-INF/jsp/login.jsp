<%@ include file="/WEB-INF/jsp/head/head.jsp" %>
	<title>ÉcoEnchères - Login</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header/header.jsp" %>
	<main>

		<h1>Identification</h1>
		<form id="login" action="<%=request.getContextPath()%>/login" method="post">
			<div class="mb-3">
				<label for="email" class="form-label">
					Adresse Email:
				</label>
				<input type="email" class="form-control" id="email" name="email" required>
			</div>
			<div class="mb-3">
				<label for="exampleInputPassword1" class="form-label">
					Mot de Passe:
				</label>
				<input type="password" class="form-control" id="password" name="password" required>
			</div>
			<button type="submit" class="btn btn-primary">S'identifier</button>
		</form>		
	</main>
	<%@ include file="/WEB-INF/jsp/footer/footer.jsp" %>	
</body>
</html>