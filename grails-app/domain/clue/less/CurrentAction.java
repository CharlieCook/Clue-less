package clue.less;

public enum CurrentAction {
	PRESTART, //Game Has not started yet
	DISPROVE, //Waiting for some player to disprove a suggestion
	TURNMOVE, //Waiting for a player to move (may suggest at this point)
	TURNSUGGEST, //Player has move and may only make suggestions
	GAMEOVER //A player has won
}
