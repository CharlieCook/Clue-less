package clue.less

/**
 * Web Controller, web client uses REST based commands, the functions handle the REST requests
 * and send responses. 
 *
 */
class CluelessController {
	
	GameEngineService gameEngineService
	
    def index() { }
	
	def createGame(){
		gameEngineService.createGame()
	}
	
	def listGames() {
		gameEngineService.listGames()
	}
	
	def joinGame(UUID, Player) {
		
	}
	
	def startGame(UUID) {
		
	}
	
	def gameState(UUID){}
	
	def move(UUID, player, location){}
	
	def suggest(UUID, player, suspect, location, weapon){}
	
	def disprove(UUID, player, card) {}
	
	def accuse(UUID, player, suspect, location, weapon){}
	
	protected GameState getGameStateFromPlayer(long playerID){
		Player player = Player.findById(playerID)
		return player.gameState
	}
}
