package clue.less

/**
 * Web Controller, web client uses REST based commands, the functions handle the REST requests
 * and send responses. 
 *
 */
class CluelessController {
	
	GameEngineService gameEngineService
	
    def index() { }
	
	def createGame(String name){
		GameState game = new GameState(name)
		game.save()
		return game.player1.id
	}
	
	def listGames() {
		def games = GameState.findAllWhere(gameStarted:false)
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
		Player player = Player.findById(playerID)
		return player.gameState
	}
}
