<html>
<body>
	So this is the GSP page for .../HelloWorld/index, grails knows this because the file is named index and it is in the helloWorld folder.
	<br>
	Very simple.
	<br>
	Not to get data in here we use the 'model' return by the controller's index method
	and invoke it by ${'$'}{helloWorld}<br>
	like so<br>
	${helloWorld}<br>
	
	This was all done with no configuration file changes, grails uses convention or configuration, simply name the files the right thing in the right location and the get automagically wired up
	<hr/>
	This is a form to create a new KeyValue pair
	<g:form name="myForm" action="makeKeyValuePair">
	key: <input type="text" name="key">
	value: <input type="text" name="value">
	<input type="submit" value="Submit">
	</g:form>
	<hr/>
	This is a form to find an existing KeyValue pair
	<g:form name="myForm" action="getKeyValuePair">
	key: <input type="text" name="key">
	<input type="submit" value="Submit">
	</g:form>
</body>
</html>