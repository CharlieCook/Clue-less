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
	
	def gameState(long playerId){
		return [game: getGameStateFromPlayer(playerId),
			playerId: playerId]
	}
	
	def move(long playerId, String location){
		log.error("Trying to move player: " + playerId + " to: " + location)
		try {
			Player player = Player.findById(playerId)
			gameEngineService.movePlayer(player, Location.valueOf(location))
			redirect(action: "gameState", params:[playerId: playerId])
		} catch(InvalidMoveException e) {
			response.status = 410
			return
		}
	}
	
	def suggest(long playerId, String suspect, String location, String weapon){
		log.error("Player: $playerId suggestion $suspect with $weapon in the $location")
		Player player = Player.findById(playerId)
		gameEngineService.makeSuggestion(player, Suspect.valueOf(suspect), 
			Weapon.valueOf(weapon), Location.valueOf(location))
		redirect(action: "gameState", params:[playerId: playerId])
	}
	
	def disprove(long playerId, String card) {
		log.error("Player: $playerId disproves with $card")
		Player player = Player.findById(playerId)
		// update the card
		player.gameState.disproven()
		player.gameState.save()
		// TODO: is the card data getting transmitted back to the client?
		redirect(action: "gameState", params:[playerId: playerId, card: card])
	}
	
	def accuse(long playerId, String suspect, String location, String weapon){
		log.error("Player: $playerId accuses $suspect with $weapon in the $location")
		Player player = Player.findById(playerId)
		gameEngineService.makeAccusation(player, Suspect.valueOf(suspect),
			Weapon.valueOf(weapon), Location.valueOf(location))
		redirect(action: "gameState", params:[playerId: playerId])
	}
	
	def nextPlayer(long playerId){
		// switch to the next player in the list
		log.info("Player: $playerId is done with the turn, switching to next player")
		Player player = Player.findById(playerId)
		player.gameState.nextPlayer()
		player.gameState.save()
		redirect(action: "gameState", params:[playerId: playerId])
	}
	
	protected GameState getGameStateFromPlayer(long playerID){
		log.info("finding game for player: " + playerID)
		Player player = Player.findById(playerID)
		return player.gameState
	}
}
