<html>
<head>
</head>
<body>
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