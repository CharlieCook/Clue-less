package clue.less

import grails.transaction.Transactional

/**
 * Handles actual manipulation of the GameState.  All of the internal logic is handled here.
 *
 */
@Transactional
class GameEngineService {

    def serviceMethod() {

    }
	
	/**
	 * Start up the game engine.
	 * @return
	 */
	def intialize() {
		
	}
	
	/**
	 * Update the game state with the new game state
	 * @param UUID - ID of the game
	 * @param GameState - new game state of the game
	 * @return Broadcast state to all users in game
	 */
	def updateGameState(UUID, GameState) {
		
	}
	
	/**
	 * Moves the corresponding player
	 * 
	 * @param UUID - player's ID
	 * @return
	 */
	def movePlayer(UUID) {
		
	}
	
	/**
	 * Handles a player making an accusation.
	 * 
	 * @param UUID - player ID making accusation
	 * @param Suspect - guessed suspect
	 * @param Weapon - guessed weapon
	 * @param Location - guessed location
	 * @return Success or failure
	 */
	def makeAccusation(UUID, Suspect, Weapon, Location) {
		
	}
	
	/**
	 * Handles a player making a suggestion.
	 * 
	 * @param UUID - player ID making suggestion
	 * @param Suspect - guessed suspect 
	 * @param Weapon - guessed weapon
	 * @param Location - guessed location
	 * @return Checks if another player has a card or returns that no one has a card
	 */
	def makeSuggestion(UUID, Suspect, Weapon, Location) {
		
	}
	
	/**
	 * Lists all available games open to join.
	 * @return list of game names a player can join
	 */
	def listGames() {
		GameState[] allGames = GameState.findAll()
		// TODO: Pass this info up to the client
	}
	
	/**
	 * Creates a new game with the given name.
	 * @param String - Name of the new game
	 * @return Game data of the new game
	 */
	def createGame(String name) {
		def uuid = UUID.randomUUID()
		while(GameState.findByUUID(uuid) != null) {
			uuid = UUID.randomUUID()
		}
		// create the new game state
		GameState newGame
		newGame.initalize(uuid, name)
		
		// Store the new game
		// TODO: Should this be 'byUUID'?
		GameState.addByGameState(newGame)
	}
	
	/**
	 * Handles a player trying to join a game
	 * @param game - ID of the game
	 * @param player - ID of the player
	 * @return Success on joining a game or not
	 */
	def joinGame(UUID game, Player player) {
		gameState = GameState.findByUUID(game)
		if(gameState != null) {
			if(gameState.players.size < GameState.MAX_PLAYERS) {
				gameState.players.add(player)
			} else {
				// TODO: Inform player game is full
			}
		} else {
			// TODO: Inform player UUID is not a valid game.
		}
	}
	
	/**
	 * Handles starting a game.
	 * @return success on game start
	 */
	def startGame() {
		
	}
}
