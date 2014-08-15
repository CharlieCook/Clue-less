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
			return new InvalidMoveException("You are already at this location")
		} else if(currentPlayer.moved) {
			log.info("Player cannot move more than once per turn")
			return new InvalidMoveException("Cannot move more than once")
		} else if(!Location.isMoveValid(currentPlayer.location, location)) {
			log.info("Player is trying to make an invalid move")
			gameState.toDo = CurrentAction.TURNMOVE
			gameState.save()
			throw new InvalidMoveException("That move is invalid")
		}

		GameState gameState = currentPlayer.gameState
		for(Player gamePlayer : gameState.getPlayers()) {
			// don't compare to self
			if(gamePlayer.id != currentPlayer.id) {
				// Check if the player can move
				 if(isHallwayOccupied(gameState, location) && !Location.isCornerRoom(location)) {
					// the player cannot move their piece
					log.info("Player cannot move from room")
					// save off the state and throw an error
					gameState.toDo = CurrentAction.TURNMOVE
					gameState.save()
					throw new InvalidMoveException("There are no valid moves from this room")
				} else {
					// update the player's location
					currentPlayer.location = location
					currentPlayer.moved = true
					if(!Location.isHallway(location)) {
						gameState.toDo = CurrentAction.TURNSUGGEST
					}
					gameState.save()
					break
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
			// player is the winner
			gameState.toDo = ToDo.GAMEOVER
			gameState.save()
		} else {
			// player can no longer take a turn
			player.accusationIncorrect = true
			gameState.nextPlayer()
			gameState.save()
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
			throw new InvalidSuggestionException("Invalid playerId: " + guessingPlayer.id)
		} else if(Location.isHallway(suggestedLocation)) {
			log.error("Player: " + guessingPlayer.id + " is trying to make a suggestion in a hallway.")
			throw new InvalidSuggestionException("Can not make suggetion in a hallway")
		} else {
			// required to check if we need to move this player
			Player suggestedPlayer = Player.findByGameStateAndSuspect(gameState, suggestedSuspect)
			if(!suggestedPlayer.location.equals(suggestedLocation)) {
				moveSuspectToken(suggestedPlayer, suggestedLocation)
			}
			
			// represents the actual player number being tracked, not the index
			int currentPlayerId = playerIndex + 1
			// loop through all player's starting with the player after the guessing player,
			// skipping the guessing player
			for(int i = 0; i < 5; ++i) {
				if(currentPlayerId >= 6) {
					currentPlayerId = 0
				}
				Player currentPlayer = gameState.getPlayers()[currentPlayerId]
				// Loop through all of the player's cards and see 
				// 		 if they have any that are matching the suggestion
				if( gameState.hasMatchingCard(currentPlayer)) {
					// Inform the player they need to show a card
					gameState.waitingOn = WaitingOn.("PLAYER" + (currentPlayerId + 1))
					gameState.toDo = CurrentAction.DISPROVE
					break
				}
				currentPlayerId++
			}
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
