package clue.less

class GameState {

	// used for getting the initial solution cards
	Random random = new Random()

	// TODO: Should this be hard coded?
	static MAX_PLAYERS = 6
		
    static constraints = {
    }
	
	/**
	 * ID of the game.
	 */
	UUID id
	
	/**
	 * User name for the created game.
	 */
	String name
	
	/**
	 * Flag indicating if the game has started yet for joining.
	 */
	boolean gameStarted
	
	/**
	 * List of players in the game.
	 */
	Player[] players
	
	/**
	 * Card representing the winning weapon.
	 */
	Weapon solutionWeapon
	
	/**
	 * Card representing the winning location.
	 */
	Location solutionLocation
	
	/**
	 * Card representing the winning suspect.
	 */
	Suspect solutionSuspect
	
	/**
	 * Sets up the variables in the game state for a new game.
	 * 
	 * @param UUID - ID of the game state object
	 * @param String - Common name of the game
	 * @return
	 */
	def initialize(UUID gameId, String gameName) {
		uuid = gameId
		name = gameName
		players = []
		gameStarted = false
		// get a random card for the weapon solution
		weapon = Weapon.getValue(random.between(0, Weapon.values().size))
		// get a random card for the location solution
		location = Location.getValue(random.between(0, Location.values().size))
		// get a random card for the suspect solution
		weapon = Suspect.getValue(random.between(0, Suspect.values().size))
	}
}
