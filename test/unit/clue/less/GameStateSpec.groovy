package clue.less

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(GameState)
@Mock([GameState, Player])
class GameStateSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test New Game is in Database"() {
		when:
			GameState game = new GameState()
			
		then:
			assertNotNull("GameState did not have an ID", game.id)
			
    }
	
	void "test New Game has Solution"(){
		when:
			GameState game = new GameState()
		
		then:
			assertNotNull("GameState did not have a solution location", game.solutionLocation)
			assertNotNull("GameState did not have a solution suspect", game.solutionSuspect)
			assertNotNull("GameState did not have a solution  weapon", game.solutionWeapon)
    }
	
	void "test Claim seat on new game"(){
		given:
			GameState game = new GameState()
		
		when:
			Player player = game.claimSeat()
			
		then:
			assertNotNull("Game did not have Player 1", player)
	}
}
