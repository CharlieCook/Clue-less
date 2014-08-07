package clue.less

import clue.less.Player

class GameState {	
	
	long id
	
	Player player1
	
	Player player2
	
	Player player3
	
	Player player4
	
	Player player5
	
	Player player6
	
	Weapon solutionWeapon
	
	Location solutionLocation
	
	Suspect solutionSuspect

    static constraints = {
    }
	
	public GameState(){
		solutionLocation = Location.HALL
		solutionWeapon = Weapon.CANDLESTICK
		solutionSuspect = Suspect.GREEN
		player1 = new Player()
		player2 = new Player()
		player3 = new Player()
		player4 = new Player()
		player5 = new Player()
		player6 = new Player()
	}

}
