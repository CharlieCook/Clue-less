<html>
<body>
Here are all the key-value pairs the server has stored
<g:each in="${keyValues}">
${it.key }:${it.value }<br>
</g:each>
</body></html>