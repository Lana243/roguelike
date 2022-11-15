package roguelike.controller.game

import roguelike.controller.Controller
import roguelike.state.Message
import roguelike.state.game.GameState
import roguelike.ui.Event
import roguelike.ui.views.View

class GameController : Controller<GameState> {
    override fun bindState(state: GameState): View {
        TODO("Not yet implemented")
    }

    override fun processEvent(event: Event): Message {
        TODO("Not yet implemented")
    }
}