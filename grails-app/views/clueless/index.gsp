<html>
<head>
<r:require modules="application" />
<r:layoutResources />
</head>
<body>
<r:layoutResources />
	<div id="gameBoard">
		<g:render template="gameBoard" />
	</div>
	<div id="rightPanelContainer">
		<div id="gameStatus">
			<g:render template="gameStatus" />
		</div>
		<div id="movementRing">
			<g:render template="movementRing" />
		</div>
	</div>

	
</body>
</html>