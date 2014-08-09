package clue.less

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GameEngineService)
class GameEngineServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test corner office detect"() {
		when:
			GameEngineService service = new GameEngineService()
		
			Location conservatory = new Location(Location.Locations.CONSERVATORY)
			Location kitchen = new Location(Location.Locations.KITCHEN)
			Location lounge = new Location(Location.Locations.LOUNGE)
			Location study = new Location(Location.Locations.STUDY)
			Location hall = new Location(Location.Locations.HALL)
		then:
			assertTrue("Conservatory should be a corner room", service.isCornerRoom(conservatory))
			assertTrue("Kitchen should be a corner room", service.isCornerRoom(kitchen))
			assertTrue("Lounge should be a corner room", service.isCornerRoom(lounge))
			assertTrue("Study should be a corner room", service.isCornerRoom(study))
			assertFalse("The hall is not a corner room", service.isCornerRoom(hall))
    }
}
