package clue.less

class Player {
	
	static belongsTo = [gameState : GameState]

    static constraints = {
			gameState lazy: false
    }
	
	long id
	
	Location location
	
//	Card[] cards
	
	public Player(){
		location = Location.HALL
	}
}
