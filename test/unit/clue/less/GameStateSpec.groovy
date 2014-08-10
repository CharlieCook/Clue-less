package clue.less

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(GameState)
@Mock(Player)
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
			game.generatePlayers()
		
		when:
			Player player = game.claimSeat()
			
		then:
			assertNotNull("Game did not have Player 1", player)
	}
	
	void "test attempt to claim seat of full game"(){
		given:
			GameState game = new GameState()
			game.generatePlayers()
			game.claimSeat()
			game.claimSeat()
			game.claimSeat()
			game.claimSeat()
			game.claimSeat()
			game.claimSeat()
		
		when:
			game.claimSeat()
			
		then:
			thrown(GameFullException)
	}
	
	void "test finding a player's number"() {
		given:
			GameState game = new GameState()
			game.generatePlayers()
			game.claimSeat()
			game.claimSeat()
			game.claimSeat()
			game.claimSeat()
			game.claimSeat()
			
		when:
			Player player = game.claimSeat()
			
		then:
			assertTrue("Player id should not be -1", game.getPlayerNumber(player) != -1)
			assertTrue("Player id should be 6", game.getPlayerNumber(player) == 6)
	}
}
