<html>
<head>
</head>
<body>
<g:form action="createGame">Create New Game: <input type="text" name="name"> <g:submitButton name="Create"/></g:form>
	<table>
		<g:each in="${games}">
			<tr>
				<td>${it.id}</td>
				<td>${it.name}</td>
				<td>${it.playerCount}</td>
				<td><g:link action='joinGame' params="${[gameId: it.id]}">Join Game</g:link></td>
			</tr>
		</g:each>
	</table>
</body>
</html>