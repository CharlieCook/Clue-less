<g:if  test="${game.players[game.waitingOn.ordinal()-1].id == playerId && game.toDo.toString() == 'TURNMOVE'}">
<g:link action="move" params="[playerId: playerId, location: room]">
<span class="${room}Room">
	<g:each in="${game.playersInRoom(room)}">
		<span id="playerIcon" class="${it.suspect}player"></span>
	</g:each>
	&nbsp	
</span>
</g:link>
</g:if>
<g:else>
<span class="${room}Room">
	<g:each in="${game.playersInRoom(room)}">
		<span id="playerIcon" class="${it.suspect}player"></span>
	</g:each>
	&nbsp	
</span>
</g:else>