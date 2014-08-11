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
				// TODO: This logic still needs work
				if(isHallwayOccupied(gameState, location) && !Location.isCornerRoom(location)) {
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
	 * Checks if the hallway given is occupied.
	 * 
	 * @param player - Player trying to move to a hallway
	 * @param location - Hallway trying to move to
	 * @return Flag indicating if the hallway given is occupied
	 */
	def isHallwayOccupied(GameState gameState, Location location) {
		// Make sure the location is a hallway
		if(!location.isHallway()) {
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
	 * Handles a player making an accusation.
	 * 
	 * @param Player - player making accusation
	 * @param Suspect - guessed suspect
	 * @param Weapon - guessed weapon
	 * @param Location - guessed location
	 * @return Success or failure
	 */
	def makeAccusation(Player player, Suspect guessedSuspect,
			Weapon guessedWeapon, Location guessedLocation) {
		GameState gameState = player.gameState
		if(gameState.solutionSuspect.equals(guessedSuspect) &&
		gameState.solutionWeapon.equals(guessedWeapon) &&
		gameState.solutionLocation.equals(guessedLocation)) {
			// TODO: Inform all players of the winner
		} else {
			player.accusationIncorrect = true;
			// TODO: Update game state
			// TODO: Inform the player their accusation is incorrect and
			// they will be skipped from moving and guessing
		}
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
	def makeSuggestion(Player guessingPlayer, Suspect suggestedSuspect,
		Weapon suggestedWeapon, Location suggestedLocation) {
		
		GameState gameState = guessingPlayer.gameState
		// run through the players in order and if they have a card that matches the guess inform them.
		int playerIndex = gameState.getPlayerNumber(guessingPLayer)
		if(playerIndex == -1) {
			log.error("Player: " + guessingPlayer.id + " is referencing a game state it is not a part of.")
			// TODO: Error, this player should have a reference to the correct game state!
		} else {
			Player[] gamePlayers = gameState.getPlayers()
			// required to check if we need to move this player
			Player suggestedPlayer = gameState.findMatchingPlayer(suggestedSuspect)
			if(!suggestedPlayer.location.equals(suggestedLocation)) {
				moveSuspectToken(suggestedPlayer, suggestedLocation)
				// TODO: Update the gameState and broadcast to all the change of player location
			}
			
			int currentPlayerIndex = playerIndex + 1
			// loop through all player's starting with the player after the guessing player,
			// skipping the guessing player
			for(int i = 0; i < 5; ++i) {
				if(currentPlayerIndex > 5) {
					currentPlayerIndex = 0
				}
				Player currentPlayer = gamePlayers[currentPlayerIndex]
				// TODO: Loop through all of the player's cards and see 
				// 		 if they have any that are matching the suggestion
				if( currentPlayer.hasMatchingCards()) {
					// TODO: Inform the player they need to show a card
					break
				}
				currentPlayerIndex++
			}
			// TODO: Do we need to set the next player's turn or is that a separate message and function?
		}
	}

	/**
	 * Handles updating the gameState by moving the suspect token to the room.
	 * 
	 * @param Suspect - Suspect in the suggestion
	 * @param Location - Location the suggestion was made
	 * @return broadcast to all player's updating their boards?
	 */
	def moveSuspectToken(Player suspect, Location location) {
		// TODO: Is this really all that is needed?
		suspect.location = location
	}

	/**
	 * Informs the next player it is their turn
	 * @param player - Current player
	 * @return The id of the next player
	 */
	def nextTurn(Player player) {
		int nextPlayerIndex = player.gameState.getPlayerIndex(player)++
		if(nextPlayerIndex > 5) {
			nextPlayerIndex = 0
		}
		// Get the next player
		Player nextPlayer = player.gameState.getPlayers()[nextPlayerIndex]
		// TODO: Inform the player of their options
		//	Does this include giving the client their options?
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
