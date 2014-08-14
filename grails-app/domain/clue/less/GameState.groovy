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
		disprovedBy nullable : true
		disprovedWith nullable:true
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
	
	Suspect disprovedBy
	Card disprovedWith
	
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
		// shuffle the deck
		int[] cardArray = shuffleCards()
		
		player1 = new Player(0, Location.HALL)
		player1.card1 = Card.values()[cardArray[0]]
		player1.card2 = Card.values()[cardArray[1]]
		player1.card3 = Card.values()[cardArray[2]]
		player1.gameState = this
		player1.save()
		player2 = new Player(1, Location.BALLROOM)
		player2.card1 = Card.values()[cardArray[3]]
		player2.card2 = Card.values()[cardArray[4]]
		player2.card3 = Card.values()[cardArray[5]]
		player2.gameState = this
		player2.save()
		player3 = new Player(2, Location.BILLARD)
		player3.card1 = Card.values()[cardArray[6]]
		player3.card2 = Card.values()[cardArray[7]]
		player3.card3 = Card.values()[cardArray[8]]
		player3.gameState = this
		player3.save()
		player4 = new Player(3, Location.STUDY)
		player4.card1 = Card.values()[cardArray[9]]
		player4.card2 = Card.values()[cardArray[10]]
		player4.card3 = Card.values()[cardArray[11]]
		player4.gameState = this
		player4.save()
		player5 = new Player(4, Location.KITCHEN)
		player5.card1 = Card.values()[cardArray[12]]
		player5.card2 = Card.values()[cardArray[13]]
		player5.card3 = Card.values()[cardArray[14]]
		player5.gameState = this
		player5.save()
		player6 = new Player(5, Location.CONSERVATORY)
		player6.card1 = Card.values()[cardArray[15]]
		player6.card2 = Card.values()[cardArray[16]]
		player6.card3 = Card.values()[cardArray[17]]
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
		// scarlet starts first
		for(int i = 0; i < 6; ++i) {
			Player p = getPlayers()[i]
			if(p.suspect.equals(Suspect.SCARLET)) {
				// +1 offset due to NONE value
				waitingOn = WaitingOn.values()[i+1]
				currentPlayer = i
			}
		}
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
	 * Returns an array representing all cards except the solution cards in the deck in numeric form.
	 * @return
	 */
	public int[] shuffleCards() {
		int index
		// create an array space for every card except the solution cards! and populate it
		int[] cardArray = new int[18]
		// indexs of cards that need to be skipped from being dealt
		int locationIndex = Card.valueOf(solutionLocation.name()).ordinal()
		int weaponIndex = Card.valueOf(solutionWeapon.name()).ordinal()
		int suspectIndex = Card.valueOf(solutionSuspect.name()).ordinal()
		
		int cardArrayIndex = 0
		for(int i = 0; i < Card.values().size(); ++i) {
			// skip the solution cards
			if(i == locationIndex ||
				i == weaponIndex ||
				i == suspectIndex) {
				// next card
				continue
			}
			cardArray[cardArrayIndex] = i
			cardArrayIndex++
		}
		
		// shuffle the array
		for(int i = cardArray.size() - 1; i > 0; --i) {
			index = random.nextInt(i)
			int temp = cardArray[index]
			cardArray[index] = cardArray[i]
			cardArray[i] = temp
		}
		
		return cardArray
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
	
	/**
	 * Simple utility to move the gameState to the next player
	 */
	public void nextPlayer() {
		int nextPlayerIndex = currentPlayer + 1
		if(nextPlayerIndex > 6) {
			nextPlayerIndex = 0
		}
		waitingOn = WaitingOn.values()[nextPlayerIndex + 1]
		toDo = CurrentAction.TURNMOVE
		currentPlayer = nextPlayerIndex
	}
	
	/**
	 * Takes the card used to disprove and sends the updated data to the player
	 * @param card
	 */
	public void disproven(Player player, Card card) {
		disprovedBy = player.suspect
		disprovedWith = card
		waitingOn = WaitingOn.values()[currentPlayer+1]
		toDo = CurrentAction.CHECKCARD
	}
}
