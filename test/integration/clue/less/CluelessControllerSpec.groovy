package clue.less

import grails.test.mixin.TestFor
import spock.lang.Specification
import static org.junit.Assert.*;

import spock.lang.*

/**
 *
 */
@TestFor(CluelessController)
class CluelessControllerSpec extends Specification {

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
}
