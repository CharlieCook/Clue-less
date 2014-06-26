package clue.less

/**
 * This is a controller, the server's interface to the web.
 * Since this controller is name HelloWorldController, it will
 * service all .../Clue-less/HelloWorld requests
 *
 */
class HelloWorldController {
	
	def helloWorldService //This will get automagically wired up too

	/**
	 * each method will auto-magically line up with a resource location
	 * such as .../Clue-less/HelloWorld/index would go here, 'index' also
	 * happens to be special in that .../HelloWorld also comes here
	 */
    def index() { 
		//If we wanted some variables passed in here they would be parameters of the function and grails
		// in most cases will auto-magically populate them from the request
		def hello = "Grails is weakly typed, so you can define variables using def or String"
		String world = "also, notice no semicolon at the end of the line, they are optional";
		return [
			helloWorld : helloWorldService.getHello(),
			ourVariable : hello + world,
			data : "we return a map of variables to values the GSP can use"
		]
	}
	
	def makeKeyValuePair(){
		def newObject = new KeyValue()
		newObject.key = params.key //params is a special variable grails provides us
		newObject.value = params.value
		newObject.save() //Saves it to Grails automagical database
		return [
			keyValues: KeyValue.findAll()
		]
	}
	
	def getKeyValuePair(){
		def foundObject = KeyValue.findByKey(params.key) //findBy[field] generates a database query on that field
		return[
			KeyValue: foundObject
		]
	}
}
