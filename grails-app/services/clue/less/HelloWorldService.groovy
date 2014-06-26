package clue.less

import grails.transaction.Transactional

/**
 * Services ideally do the work, in our case we may use a service to manage the game
 * it keeps the interface code in the controller and the game code here
 *
 */
@Transactional
class HelloWorldService {

    def serviceMethod() {

    }
	
	def getHello(){
		return "Hello World"
	}
}
