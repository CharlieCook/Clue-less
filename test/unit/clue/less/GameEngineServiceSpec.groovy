package clue.less

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GameEngineService)
@Mock([GameState, Player])
class GameEngineServiceSpec extends Specification {

	GameEngineService gameEngine = new GameEngineService()
	
    def setup() {
    }

    def cleanup() {
    }

    void "test isHallwayOccupied with occupied hallway"() {
		given:
			GameState game = new GameState()
			game.save()
			game.generatePlayers()
			game.player1.location = Location.HALLWAY1
		
		when:
			def result = gameEngine.isHallwayOccupied(game, Location.HALLWAY1)
		
		then:
			assertTrue("Hallway was shown empty even though it was occupied", result)
		
    }
	
	void "test isHallwayOccupied without occupied hallway"() {
		given:
			GameState game = new GameState()
			game.save()
			game.generatePlayers()
			game.player1.location = Location.HALLWAY1
		
		when:
			def result = gameEngine.isHallwayOccupied(game, Location.HALLWAY2)
		
		then:
			assertFalse("Hallway was shown occupied even though it was empty", result)
		
	}
}
