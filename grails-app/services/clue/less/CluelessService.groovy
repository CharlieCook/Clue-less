package clue.less

import grails.transaction.Transactional

@Transactional
class CluelessService {

    def serviceMethod() {

    }
	
	GameState newGame(){
		def game = new GameState()
		return game
	}
}
