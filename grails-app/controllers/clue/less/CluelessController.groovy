package clue.less

import org.apache.commons.logging.LogFactory

/**
 * Web Controller, web client uses REST based commands, the functions handle the REST requests
 * and send responses. 
 *
 */
class CluelessController {
	
	GameEngineService gameEngineService
	
	private static final log = LogFactory.getLog(this)
	
    def index() { }
	
	def createGame(String name){
		GameState game = new GameState(name)
		game.save()
		log.info("Created game: " + game.id)
		return game.claimSeat().id
	}
	
	def listGames(int max, int offset) {
		if(max == null || max == 0) max = 10
		if(offset == null) offset = 0
		def games = GameState.findAllByGameStarted(false,
			[max:max, offset: offset, sort: "name", order:"asc"])
		return games;
	}
	
	def joinGame(gameId) {
		
	}
	
	def startGame(id) {
		
	}
	
	def gameState(id){
		return getGameStateFromPlayer(id)
	}
	
	def move(UUID, player, location){}
	
	def suggest(UUID, player, suspect, location, weapon){}
	
	def disprove(UUID, player, card) {}
	
	def accuse(UUID, player, suspect, location, weapon){}
	
	protected GameState getGameStateFromPlayer(long playerID){
		log.info("finding game for player: " + playerID)
		Player player = Player.findById(playerID)
		return player.gameState
	}
}
