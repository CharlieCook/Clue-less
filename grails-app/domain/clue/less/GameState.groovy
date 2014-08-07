package clue.less

import clue.less.Player

class GameState {	
	
	long id
	
	static constraints = {
	}
	
	// Having each player as an instance variable instead of an array, we simplify hibernate configuration to 0
	Player player1
	
	Player player2
	
	Player player3
	
	Player player4
	
	Player player5
	
	Player player6

	// used for getting the initial solution cards
	Random random = new Random()
	
	/**
	 * User name for the created game.
	 */
	String name
	
	/**
	 * Flag indicating if the game has started yet for joining.
	 */
	boolean gameStarted
	
	/**
	 * Card representing the winning weapon.
	 */
	Weapon solutionWeapon
	
	/**
	 * Card representing the winning location.
	 */
	Location solutionLocation
	
	/**
	 * Card representing the winning suspect.
	 */
	Suspect solutionSuspect
	
	/**
	 * Default constructor required by hibernate
	 */
	public GameState(){
		generatePlayers()
		generateRandomSolution()
		name = "" + new Date()
		gameStarted = false
	}

	public GameState(String gameName){
		generatePlayers()
		generateRandomSolution()		
		name = gameName
		gameStarted = false
	}
	
	private void generatePlayers(){
		player1 = new Player()
		player2 = new Player()
		player3 = new Player()
		player4 = new Player()
		player5 = new Player()
		player6 = new Player()
	}
	
	private void generateRandomSolution(){
		// get a random card for the weapon solution
		solutionWeapon = Weapon.values()[random.nextInt(Weapon.values().size()-1)]
		// get a random card for the location solution
		solutionLocation = Location.values()[random.nextInt(Location.values().size()-1)]
		// get a random card for the suspect solution
		solutionSuspect = Suspect.values()[random.nextInt(Suspect.values().size()-1)]
	}
	
	public Player[] getPlayers(){
		return [player1, player2, player3, player4, player5, player6]
	}
}
