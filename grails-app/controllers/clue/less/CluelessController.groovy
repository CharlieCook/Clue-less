package clue.less

class CluelessController {
	
	def cluelessService;

    def index() { }
	
	def createGame(){}
	
	def gameState(UUID){}
	
	def move(UUID, player, location){}
	
	def suggest(UUID, player, suspect, location, weapon){}
	
	def disprove(UUID, player, card) {}
	
	def accuse(UUID, player, suspect, location, weapon){}
}
