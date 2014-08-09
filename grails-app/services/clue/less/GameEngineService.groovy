package clue.less

import org.apache.commons.logging.LogFactory;

import grails.transaction.Transactional

/**
 * Handles actual manipulation of the GameState.  All of the internal logic is handled here.
 *
 */
@Transactional
class GameEngineService {

	private static final log = LogFactory.getLog(this)
	
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
	def updateGameState(UUID, GameState newState) {
		// TODO: Need input validation?
		GameState.addByGameState(newState)
		// TODO: Broadcast the updated state to all players
	}
	
	/**
	 * Moves the corresponding player
	 * 
	 * @param Player - player that is attempting to move
	 * @return
	 */
	def movePlayer(Player currentPlayer, GameState gameState) {
		for(Player gamePlayer : gameState.getPlayers()) {
			if(gamePlayer.id != currentPlayer.id) {
				// Don't have to compare to self
				// TODO: If moving to hallway make sure it is empty
				if(hallwayOccupied == true && !isCornerOffice(currentPlayer.location)) {
					log.info("Player cannot move from room")
					// TODO: Inform client that move is not ok
				} else {
					for(Player player : gameState.getPlayers()) {
						// update the location
						if(player.id == currentPlayer.id) {
							player.location = currentPlayer.location
						}
						// store the update
						GameState.addByGameState(gameState)
						// TODO: Inform the user the move was a success
						// TODO: Inform the next player it is there turn
					}
				}
			}
		}
	}
	
	/**
	 * Simply checks if the location card is a corner office
	 */
	def isCornerRoom(Location location) {
		if(location == Location.KITCHEN ||
			location == Location.STUDY ||
			location == Location.CONSERVATORY ||
			location == Location.LOUNGE) {
			return true
		}
		return false
	}
	
	/**
	 * Handles a player making an accusation.
	 * 
	 * @param Player - player making accusation
	 * @param Suspect - guessed suspect
	 * @param Weapon - guessed weapon
	 * @param Location - guessed location
	 * @return Success or failure
	 */
	def makeAccusation(Player, Suspect, Weapon, Location) {
		
	}
	
	/**
	 * Handles a player making a suggestion.
	 * 
	 * @param Player - player ID making suggestion
	 * @param Suspect - guessed suspect 
	 * @param Weapon - guessed weapon
	 * @param Location - guessed location
	 * @return Checks if another player has a card or returns that no one has a card
	 */
	def makeSuggestion(Player, Suspect, Weapon, Location) {
		
	}
	
	/**
	 * Handles updating the gameState by moving the suspect token to the room.
	 * 
	 * @param Suspect - Suspect in the suggestion
	 * @param Location - Location the suggestion was made
	 * @return broadcast to all player's updating their boards
	 */
	def moveSuspectToken(Suspect, Location) {
		
	}
	
	/**
	 * Informs the next player it is their turn
	 */
	def nextTurn(Player) {
		// TODO: check if the player is in a room and can move
		// TODO: Inform the player of their options
	}
	
	/**
	 * Handles starting a game.
	 * @return success on game start
	 */
	def startGame(GameState gameState) {
		if(gameState.getPlayers().size < 2) {
			// TODO: Game cannot be started too few people
		} else {
			// TODO: Do we dish out the cards now?
			gameState.gameStarted = true
			GameState.addByGameState(gameState)
			for( Player player : gameState.getPlayers()) {
				if(player.suspect == Suspect.SCARLET) {
					// TODO: Inform that player it is their turn, scarlet always goes first
					
				}
			}
			// TODO: Send out an error message?  We should not have gotten here? Scarlet should be in the game	
		}
	}
}
