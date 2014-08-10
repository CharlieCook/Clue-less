package clue.less

class Player {
	long id
	
	GameState gameState
	
	boolean claimed = false

	Location location
	
	/**
	 * The player's character
	 */
	Suspect suspect
	
	/**
	 * Flag indicating the player has made an incorrect accusation.
	 * Player can't move or make guesses, only show their cards to other players
	 */
	boolean accusationIncorrect = false
	
//	Card[] cards
	
	public Player(int suspectIndex){
		location = Location.HALL
		suspect = Suspect.values()[suspectIndex]
	}
}
