<g:form >
	<input type="hidden" name="playerId" value="${playerId}">
	<u>Suspect:</u><br>
	<table>
		<tr>
			<td><input type="radio" name="suspect" value="SCARLET" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Miss Scarlet</td>
			<td><input type="radio" name="suspect" value="MUSTARD" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Col. Mustard</td>
			<td><input type="radio" name="suspect" value="WHITE" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Mrs. White</td>
		</tr>
		<tr>
			<td><input type="radio" name="suspect" value="GREEN" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Mr.Green</td>
			<td><input type="radio" name="suspect" value="PEACOCK" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Mrs. Peacock</td>
			<td><input type="radio" name="suspect" value="PLUM" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Prof. Plum</td>
		</tr>
	</table>
	<br><br>
	<u>Weapon:</u>
	<table><tr>
			<td><input type="radio" name="weapon" value="CANDLESTICK" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Candle Stick</td>
			<td><input type="radio" name="weapon" value="WRENCH" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Wrench</td>
			<td><input type="radio" name="weapon" value="ROPE" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Rope</td>
		</tr>
		<tr>
			<td><input type="radio" name="weapon" value="REVOLVER" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Revolver</td>
			<td><input type="radio" name="weapon" value="KNIFE" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Knife</td>
			<td><input type="radio" name="weapon" value="PIPE" ${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'disabled':''}>Lead Pipe</td>
		</tr>
	</table>
	<br>  <br>
	<u>Location:</u><input type="hidden" name="location" value="${game.players[game.waitingOn.ordinal()-1].location}">
	<g:if  test="${game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST')}">
		${game.players[game.waitingOn.ordinal()-1].location}
	</g:if>
<br>
	<br> <g:actionSubmit value="Make Accusation" action="accuse" disabled="${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'true':'false'}"/>
	<g:actionSubmit value="Make Suggestion" action="suggest" disabled="${!(game.players[game.waitingOn.ordinal()-1].id == playerId && (game.toDo.toString() == 'TURNMOVE' || game.toDo.toString() == 'TURNSUGGEST'))?'true':'false'}"/>
</g:form>