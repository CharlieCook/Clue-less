package clue.less

class Player {
	
	static belongsTo = [gameState : GameState]

    static constraints = {
			gameState lazy: false
    }
	
	long id
	
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
	boolean accusationIncorrect
	
//	Card[] cards
	
	public Player(){
		location = Location.HALL
		suspect = Suspect.SCARLET
	}
}
