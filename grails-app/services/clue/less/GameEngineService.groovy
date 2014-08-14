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
					// save off the state and throw an error
					gameState.toDo = CurrentAction.TURNMOVE
					gameState.save()
					return new InvalidMoveException("There are no valid moves from this room")
				} else {
					// update the player's location
					currentPlayer.location = location
					// TODO: Does this save off the game state correctly?
					if(!Location.isHallway(location)) {
						gameState.toDo = CurrentAction.TURNSUGGEST
					}
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
		if(!Location.isHallway(location)) {
			log.info("Location: " + location.name() + " is not a hallway")
			return false
		}
		// loop through all the players and see if any are in the hallway
		if(Player.countByGameStateAndLocation(gameState, location) >= 1) {
			return true
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
		// store the current suggested suspect, weapon, and location in the game state
		gameState.suggestionWeapon = suggestedWeapon
		gameState.suggestionLocation = suggestedLocation
		gameState.suggestionSuspect = suggestedSuspect
		// run through the players in order and if they have a card that matches the guess inform them.
		int playerIndex = gameState.currentPlayer
		if(playerIndex < 0 || playerIndex > 5) {
			log.error("Player: " + guessingPlayer.id + " is referencing a game state it is not a part of.")
			// TODO: Error, this player should have a reference to the correct game state!
		} else if(Location.isHallway(suggestedLocation)) {
			log.error("Player: " + guessingPlayer.id + " is trying to make a suggestion in a hallway.")
			throw new InvalidMoveException("Can not make suggetion in a hallway")
		} else {
			// required to check if we need to move this player
			Player suggestedPlayer = Player.findByGameStateAndSuspect(gameState, suggestedSuspect)
			if(!suggestedPlayer.location.equals(suggestedLocation)) {
				moveSuspectToken(suggestedPlayer, suggestedLocation)
				// TODO: Update the gameState and broadcast to all the change of player location
			}
			
			// represents the actual player number being tracked, not the index
			int currentPlayerId = playerIndex + 1
			// loop through all player's starting with the player after the guessing player,
			// skipping the guessing player
//			boolean playerHasCard = false
			for(int i = 0; i < 5; ++i) {
				if(currentPlayerId > 6) {
					currentPlayerId = 0
				}
				Player currentPlayer = gameState.getPlayers()[currentPlayerId]
				// TODO: Loop through all of the player's cards and see 
				// 		 if they have any that are matching the suggestion
				if( gameState.hasMatchingCard(currentPlayer)) {
					// TODO: Inform the player they need to show a card
					gameState.waitingOn = WaitingOn.("PLAYER" + (currentPlayerId + 1))
					gameState.toDo = CurrentAction.DISPROVE
					//TODO add fields for current suggestion
//					playerHasCard = true
					break
				}
				currentPlayerId++
			}
			// No player has a card to disprove the suggestion, next player's turn
			// TODO: Do we allow the player a chance to make an accusation first?
//			if(!playerHasCard) {
//				gameState.nextPlayer()
//				gameState.save()
//			}
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
		suspect.location = location
	}
}
