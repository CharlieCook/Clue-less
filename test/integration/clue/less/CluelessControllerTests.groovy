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
			Player p1 = game.claimSeat()
	
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
	
	void "test list game returns a list of games"(){
		given:
			GameState game1 = new GameState()
			game1.save()
			GameState game2 = new GameState()
			game2.save()
			GameState game3 = new GameState()
			game3.save()
		
		when:
			def games = controller.listGames()
			
		then:
			assertEquals("Controller returned the wrong number of games", 3, games.size())
		
	}
	
	void "test list game returns maximun number specified"(){
		given:
			GameState game1 = new GameState()
			game1.save()
			GameState game2 = new GameState()
			game2.save()
			GameState game3 = new GameState()
			game3.save()
		
		when:
			def games = controller.listGames(2, 0)
			
		then:
			assertEquals("Controller listGames did not respect the max paramenter", 2, games.size())
	}
	
	void "test list game returns offset specified"(){
		given:
			GameState game1 = new GameState("A")
			game1.save()
			GameState game2 = new GameState("B")
			game2.save()
			GameState game3 = new GameState("C")
			game3.save()
		
		when:
			def games = controller.listGames(5, 1)
			
		then:
			assertEquals("Controller listGames did not respect the max paramenter", game2.name, games[0].name)
	}
	
	void "test Join open game"(){
		given:
			controller.createGame();
			def gameId = controller.listGames()[0].id
		
		when:
			long playerId = controller.joinGame(gameId)
		
		then:
			assertEquals("Player Id did not attach to game", gameId, controller.getGameStateFromPlayer(playerId).id)
	}
	
	void "test Join full game"(){
		given:
			controller.createGame();
			def gameId = controller.listGames()[0].id
			controller.joinGame(gameId)
			controller.joinGame(gameId)
			controller.joinGame(gameId)
			controller.joinGame(gameId)
			controller.joinGame(gameId)
		
		when:
			controller.joinGame(gameId)
		
		then:
			assertEquals("Controller did not return a 410 for a gone seat", 410, controller.response.status)
	}
}
