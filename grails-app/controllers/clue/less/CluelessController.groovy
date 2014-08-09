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
		GameState game = new GameState()
		game.createGame(name)
		game.save()
		game.generatePlayers()
		redirect(action: "joinGame", params:[gameId: game.id])
		return [game: game]
	}
	
	def listGames(int max, int offset) {
		if(max == null || max == 0) max = 10
		if(offset == null) offset = 0
		def games = GameState.findAllByGameStarted(false,
			[max:max, offset: offset, sort: "name", order:"asc"])
		def gameCount = GameState.countByGameStarted(false)
		return [games: games, gameCount: gameCount, max: max, offset:offset]
	}
	
	def joinGame(long gameId) {
		GameState game = GameState.findById(gameId)
		try{
			Player player = game.claimSeat()
			redirect(action: "gameState", params:[playerId: player.id])
		} catch (GameFullException e){
			response.status = 410 //Resource 'Gone'
			return
		}
	}
	
	def startGame(id) {
		
	}
	
	def gameState(long playerId){
		return [game: getGameStateFromPlayer(playerId)]
	}
	
	def move(long playerId, Location location){
		log.info("Trying to move player: " + playerId)
		Player player = Player.findById(playerID)
		gameEngineService.movePlayer(player, location)
	}
	
	def suggest(UUID, player, suspect, location, weapon){}
	
	def disprove(UUID, player, card) {}
	
	def accuse(UUID, player, suspect, location, weapon){}
	
	protected GameState getGameStateFromPlayer(long playerID){
		log.info("finding game for player: " + playerID)
		Player player = Player.findById(playerID)
		return player.gameState
	}
}
