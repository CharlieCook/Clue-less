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
	def movePlayer(Player currentPlayer, Location location) {
		if(currentPlayer.location.equals(location)) {
			log.info("Cannot move to the same location")
			// TODO: inform player you cannot move to the same location
		}
		
		GameState gameState = currentPlayer.gameState
		for(Player gamePlayer : gameState.getPlayers()) {
			// don't compare to self
			if(gamePlayer.id != currentPlayer.id) {
				// TODO: If moving to hallway make sure it is empty
				if(hallwayOccupied == true && !isCornerOffice(currentPlayer.location)) {
					log.info("Player cannot move from room")
					// TODO: Inform client that move is not ok
				} else {
					// update the player's location
					player.location = location
					// TODO: Does this save off the game state correctly?
					gameState.save()
					// TODO: Update all clients with the new game state
					// TODO: Inform the next player it is their turn, is this the controller's job?
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
	 * Checks if the hallway given is occupied.
	 * 
	 * @param player - Player trying to move to a hallway
	 * @param location - Hallway trying to move to
	 * @return Flag indicating if the hallway given is occupied
	 */
	def isHallwayOccupied(GameState gameState, Location location) {
		// Make sure the location is a hallway
		if(!isHallway(location)) {
			log.info("Location: " + location.value() + " is not a hallway")
			return false
		}
		// loop through all the players and see if any are in the hallway
		for(Player player : gameState.getPlayers()) {
			if(player.location.equals(location)) {
				return true
			}
		}
		// hallway is not occupied
		return false
	}
	
	/**
	 * Simple utility for detecting if a location is a hallway.
	 * 
	 * @param location - Location to check if it is a hallway
	 * @return Indication if the location is a hallway
	 */
	def isHallway(Location location) {
		if(location.equals(Location.HALLWAY1) ||
			location.equals(Location.HALLWAY2) ||
			location.equals(Location.HALLWAY3) ||
			location.equals(Location.HALLWAY4) ||
			location.equals(Location.HALLWAY5) ||
			location.equals(Location.HALLWAY6) ||
			location.equals(Location.HALLWAY7) ||
			location.equals(Location.HALLWAY8) ||
			location.equals(Location.HALLWAY9) ||
			location.equals(Location.HALLWAY10) ||
			location.equals(Location.HALLWAY11) ||
			location.equals(Location.HALLWAY12)) {
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
