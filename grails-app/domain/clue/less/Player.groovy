package clue.less

class Player {
	long id
	
	static transients = ['cards']
	
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
	
	/**
	 * Flag indicating if the player has moved during their turn, prevents double moves.
	 */
	boolean moved = false
	
	Card card1	
	Card card2
	Card card3
	
	public Player(int suspectIndex, Location location){
		this.location = location
		suspect = Suspect.values()[suspectIndex]
		card1 = Card.BALLROOM
		card2 = Card.DINING
		card3 = Card.LOUNGE
	}
	
	public void setCards(Card[] cards){
		if(cards.size() != 3){
			throw new Exception("Must have 3 cards")
		}
		
		card1 = cards[0]
		card2 = cards[1]
		card3 = cards[2]
	}
	
	public Card[] getCards(){
		return [card1, card2, card3]
	}
	
	/**
	 * Checks if the suggested card is in this player's hand.
	 * @param suggestedCard - Card to be checked against all of this player's cards
	 * @return Indication if this player has a card that matches the suggestion card
	 */
	public boolean hasCard(Card suggestedCard) {
		if(suggestedCard.equals(card1) ||
			suggestedCard.equals(card2) ||
			suggestedCard.equals(card3)) {
			return true
		}
		return false
	}
}
