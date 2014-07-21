package clue.less

class GameState {

    static constraints = {
    }
	
	UUID id
	
	Player[] players
	
	Weapon solutionWeapon
	
	Location solutionLocation
	
	Suspect solutionSuspect
}
