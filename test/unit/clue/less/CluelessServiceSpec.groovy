package clue.less

import grails.test.mixin.TestFor
import spock.lang.Specification
import static org.junit.Assert.*;

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CluelessService)
class CluelessServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test New Game is in Database"() {
		when:
			GameState game = service.newGame()
			
		then:
			assertNotNull("GameState did not have an ID", game.id)
			
    }
	
	void "test New Game has Solution"(){
		when:
			GameState game = service.newGame()
		
		then:
			assertNotNull("GameState did not have a solution location", game.solutionLocation)
			assertNotNull("GameState did not have a solution suspect", game.solutionSuspect)
			assertNotNull("GameState did not have a solution  weapon", game.solutionWeapon)
    }
	
	void "test New Game Has Players"(){
		when:
			GameState game = service.newGame()
			
		then:
			assertNotNull("Game did not have Player 1", game.player1)
			assertNotNull("Game did not have Player 2", game.player2)
			assertNotNull("Game did not have Player 3", game.player3)
			assertNotNull("Game did not have Player 4", game.player4)
			assertNotNull("Game did not have Player 5", game.player5)
			assertNotNull("Game did not have Player 6", game.player6)
	}
}
