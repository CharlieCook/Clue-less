package clue.less

import clue.less.GameFullException
import clue.less.Player

class GameState {	
	
	/**
	 * This will be the UUID of the game
	 * it is automatically generated by the database and is immuttable
	 */
	long id
	
	static constraints = {
		player1 nullable: true
		player2 nullable: true
		player3 nullable: true
		player4 nullable: true
		player5 nullable: true
		player6 nullable: true
		suggestionSuspect nullable: true
		suggestionWeapon nullable: true
		suggestionLocation nullable: true
	}
	
	static transients = ['playerCount']
	
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
	
	int currentPlayer
	
	WaitingOn waitingOn = WaitingOn.NONE
	
	CurrentAction toDo = CurrentAction.PRESTART
	
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
	 * Holds the current values of the suggestion	
	 */
	Suspect suggestionSuspect
	
	Weapon suggestionWeapon
	
	Location suggestionLocation
	
	/**
	 * Default constructor required by hibernate
	 */
	public GameState(){
		createGame(""+new Date())
	}

	
	public GameState createGame(String name){
		generateRandomSolution()
		this.name = name?:""+new Date()
		gameStarted = false		
		return this
	}
	
	private void generateRandomSolution(){
		// get a random card for the weapon solution
		solutionWeapon = Weapon.values()[random.nextInt(Weapon.values().size()-1)]
		// get a random card for the location solution
		solutionLocation = Location.values()[random.nextInt(Location.getNonHallways().size()-1)]
		// get a random card for the suspect solution
		solutionSuspect = Suspect.values()[random.nextInt(Suspect.values().size()-1)]
	}
	
	/**
	 * Generates the players and assigns them a number 1 through 6 for which suspect they are
	 */
	private void generatePlayers(){
		player1 = new Player(0)
		player1.gameState = this
		player1.save()
		player2 = new Player(1)
		player2.gameState = this
		player2.save()
		player3 = new Player(2)
		player3.gameState = this
		player3.save()
		player4 = new Player(3)
		player4.gameState = this
		player4.save()
		player5 = new Player(4)
		player5.gameState = this
		player5.save()
		player6 = new Player(5)
		player6.gameState = this
		player6.save()
	}
	
	public Player claimSeat(){
		for(Player p: getPlayers()){
			if(!p.claimed){
				p.claimed = true
				if(getPlayerCount() == 6){
					startGame()
				}
				return p
			}
		}
		throw new GameFullException("No Seats available")
	}
	
	public void startGame(){
		currentPlayer = 1
		waitingOn = WaitingOn.PLAYER1
		toDo = CurrentAction.TURNMOVE
		gameStarted = true
	}
	
	int getPlayerCount(){
		int x = 0
		for(Player p: getPlayers()){
			if(p.claimed){
				x++
			}
		}
		return x
	}
	
	public Player[] getPlayers(){
		return [player1, player2, player3, player4, player5, player6]
	}
	
	/**
	 * Gets the player's number, it is used for getting the next player's turn
	 * and finding out in order which player needs to reveal a card during a suggestion.
	 * @param player - Player that needs its number found
	 * @return 1 - 6 indicates the player number, -1 means that player id is NOT in this game state
	 */
	public int getPlayerNumber(Player player) {
		if(player.id == player1.id) {
			return 1
		} else if(player.id == player2.id) {
			return 2
		} else if(player.id == player3.id) {
			return 3
		} else if(player.id == player4.id) {
			return 4
		} else if(player.id == player5.id) {
			return 5
		} else if(player.id == player6.id) {
			return 6
		}
		return -1 
	}
	
	/**
	 * During a suggestion checks if a player has a matching card.
	 * @param player - Player being checked against the current suggestion cards
	 * @return Indication if the player has a card for the suggestion
	 */
	public boolean hasMatchingCard(Player player){
		if(player.hasCard(Card.valueOf(suggestionWeapon.name())) ||
			player.hasCard(Card.valueOf(suggestionLocation.name())) ||
			player.hasCard(Card.valueOf(suggestionSuspect.name()))) {
			return true
		}
		return false
	}
	
	public List playersInRoom(String strLoc){
		Location loc = Location.valueOf(strLoc)
		List result = []
		for(Player p: getPlayers()){
			if(p.location == loc){
				result.add(p)
			}
		}
		return result
	}
}
