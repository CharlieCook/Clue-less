<html>
<head>
<r:require modules="listView" />
<r:layoutResources />
</head>
<body>
	<r:layoutResources />
	<g:form action="createGame">Create New Game: <input type="text" name="name">
		<g:submitButton name="Create" />
	</g:form>
	<g:paginate total="${gameCount}" action="listGames" max="${max}" offset="${offset}"/>
	<table class="gameList">
		<col width="1*">
		<col width="4*">
		<col width="2*">
		<col width="2*">
		<tr>
			<td>id</td>
			<td>Game Name</td>
			<td>Players</td>
			<td>Join</td>
		</tr>
		<g:each in="${games}">
			<tr>
				<td>${it.id}</td>
				<td>${it.name}</td>
				<td>${it.playerCount}/6</td>
				<td><g:link action='joinGame' params="${[gameId: it.id]}">Join Game</g:link></td>
			</tr>
		</g:each>
	</table>
</body>
</html>