<head>
<g:if  test="${game.players[game.waitingOn.ordinal()-1].id != playerId}"><script type="text/javascript">
<%--Reloads the page every 3000ms, except for the Waiting on player since that could get annoying if the page reloads as you are trying to type--%>
window.setTimeout("window.location.reload()",3000)
</script>
</g:if>

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
		<div id="cardsBox">
			<g:render template="cards" />
		</div>
		<div id=suggestionbox>
			<g:render template="suggestionBox" />
		</div>
	</div>
</body>