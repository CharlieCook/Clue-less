<g:form action="disprove">Your Cards<br/>
<g:each in="${game.getPlayers()}">
	<g:if test="${it.id == playerId}">
		<table>
			<tr>
				<td><input type="radio" name="card" value="${it.card1}" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'DISPROVE' && (it.card1.toString() == game.suggestionSuspect.toString() || it.card1.toString() == game.suggestionWeapon.toString() || it.card1.toString() == game.suggestionLocation.toString()))?'disabled':''}>${it.card1}	</td>
				<td><input type="radio" name="card" value="${it.card2}" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'DISPROVE' && (it.card2.toString() == game.suggestionSuspect.toString() || it.card2.toString() == game.suggestionWeapon.toString() || it.card2.toString() == game.suggestionLocation.toString()))?'disabled':''}>${it.card2}	</td>
				<td><input type="radio" name="card" value="${it.card3}" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'DISPROVE' && (it.card3.toString() == game.suggestionSuspect.toString() || it.card3.toString() == game.suggestionWeapon.toString() || it.card3.toString() == game.suggestionLocation.toString()))?'disabled':''}>${it.card3}	</td>
			</tr>
		</table>
	</g:if>
</g:each>
	<input type="hidden" value="${playerId}" name="playerId">
	<g:submitButton name="Disprove" disabled="${!(game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'DISPROVE')?'true':'false'}"/>
</g:form>