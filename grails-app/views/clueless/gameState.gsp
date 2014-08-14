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
${game.name}
Status:<br/>
Player1: ${game.player1.claimed}, ${game.player1.cards[0]}<br/>
Player2: ${game.player2.claimed}, ${game.player2.cards[0]}<br/>
Player3: ${game.player3.claimed}, ${game.player3.cards[0]}<br/>
Player4: ${game.player4.claimed}, ${game.player4.cards[0]}<br/>
Player5: ${game.player5.claimed}, ${game.player5.cards[0]}<br/>
Player6: ${game.player6.claimed}, ${game.player6.cards[0]}<br/>
<br/>
Current Player: ${game.currentPlayer}<br/>
Waiting on player: ${game.waitingOn}, to do ${game.toDo}<br/>
<g:if  test="${game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'TURNMOVE'}">
<g:form action="move">move to <input type="text" name="location">
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="move" />
</g:form>

<g:form action="suggest">suggest<br/>
Suspect: <input type="text" name="suspect"> <br/>
Weapon: <input type="text" name="weapon"> <br/>
location: <input type="text" name="location"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="suggest" />
</g:form>

<g:form action="accuse">Accuse<br/>
Suspect: <input type="text" name="suspect"> <br/>
Weapon: <input type="text" name="weapon"> <br/>
location: <input type="text" name="location"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="Accuse" />
</g:form>
</g:if>

<g:if  test="${game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'TURNSUGGEST'}">
<g:form action="suggest">suggest<br/>
Suspect: <input type="text" name="suspect"> <br/>
Weapon: <input type="text" name="weapon"> <br/>
location: <input type="text" name="location"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="suggest" />
</g:form>

<g:form action="accuse">Accuse<br/>
Suspect: <input type="text" name="suspect"> <br/>
Weapon: <input type="text" name="weapon"> <br/>
location: <input type="text" name="location"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="Accuse" />
</g:form>
</g:if>

<g:if  test="${game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'DISPROVE'}">
<g:form action="disprove">disprove<br/>
Card: <input type="text" name="card"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="Disprove" />
</g:form>
</g:if>
</body>