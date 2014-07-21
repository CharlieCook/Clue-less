package clue.less

class GameState {

    static constraints = {
    }
	
	UUID id
	
	Player[] players
	
	Weapons solutionWeapon
	
	Locations solutionLocation
	
	Suspects solutionSuspect
}
