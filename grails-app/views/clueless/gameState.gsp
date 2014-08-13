${game.name}
Status:<br/>
Player1: ${game.player1.claimed}, ${game.player1.cards[0]}<br/>
Player2: ${game.player2.claimed}, ${game.player2.cards}<br/>
Player3: ${game.player3.claimed}<br/>
Player4: ${game.player4.claimed}<br/>
Player5: ${game.player5.claimed}<br/>
Player6: ${game.player6.claimed}<br/>
<br/>
Current Player: ${game.currentPlayer}<br/>
Waiting on player: ${game.waitingOn}, to do ${game.toDo}<br/>
<g:form action="move">move to <input type="text" name="location">
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="move" />
</g:form>
<br/>
<g:form action="suggest">suggest<br/>
Suspect: <input type="text" name="suspect"> <br/>
Weapon: <input type="text" name="weapon"> <br/>
location: <input type="text" name="location"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="suggest" />
</g:form>
<br/>
<g:form action="disprove">disprove<br/>
Card: <input type="text" name="card"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="Disprove" />
</g:form>
<br/>
<g:form action="accuse">Accuse<br/>
Suspect: <input type="text" name="suspect"> <br/>
Weapon: <input type="text" name="weapon"> <br/>
location: <input type="text" name="location"> <br/>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="Accuse" />
</g:form>