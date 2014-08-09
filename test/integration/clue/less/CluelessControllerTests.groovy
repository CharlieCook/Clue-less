package clue.less

/**
 * This is an integration test, it must be ran from the command line via
 *  'grails test-app'
 */
class CluelessControllerTests extends GroovyTestCase  {

	def controller = new CluelessController()
    def setup() {
    }

    def cleanup() {
    }

    void "test Retrieve Game by Player ID"() {
		given:
			GameState game = new GameState()
			game.save()
			Player p1 = game.player1
			p1.save()
	
		when:
			def foundGame = controller.getGameStateFromPlayer(p1.id)
			
		then:
			assertEquals("Wrong game was returned from player ID", game, foundGame)
    }
	
	void "test create game returns valid player id"(){
		when:
			long playerId = controller.createGame()
		
		then:
			assertNotNull("GameState seems to not valid", controller.getGameStateFromPlayer(playerId))			 
	}
}
