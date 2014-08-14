
<span id="playerIcon" class="${game.players[game.waitingOn.ordinal()-1].suspect}Player"></span>
<span id="currentPlayer">Waiting on: ${game.waitingOn}</span> <br>
<span id="currentAction">to do: ${game.toDo}</span> <br/>
<g:if test="${game.suggestionSuspect}">
<span><g:if test="${game.toDo.toString() == 'DISPROVE'}"> Current Suggestion:<br/>
	It was ${game.suggestionSuspect}<br/>
	in the ${game.suggestionLocation}<br/>
	with the ${game.suggestionWeapon}
</g:if>
<g:elseif test="${game.toDo.toString() == 'CHECKCARD' && game.players[game.waitingOn.ordinal()-1].id == playerId}"> ${game.disprovedBy} disproved you<br/>
by showing the card: ${game.disprovedWith}
<g:form action="nextPlayer">
<input type="hidden" value="${playerId}" name="playerId">
<g:submitButton name="End Turn" />
</g:form>
</g:elseif>
<g:else> Last Suggestion disproven by: ${game.disprovedBy}<br/>
	It was ${game.suggestionSuspect}
	in the ${game.suggestionLocation}
	with the ${game.suggestionWeapon}
</g:else>
</span>
</g:if>