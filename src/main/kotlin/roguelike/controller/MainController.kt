package roguelike.controller

import roguelike.controller.game.GameController
import roguelike.controller.menu.MenuController
import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.GameState
import roguelike.state.menu.MenuScreenState
import roguelike.ui.Event
import roguelike.ui.views.View

/**
 * Композиция всех котроллеров
 */
class MainController(
    var state: State
) : Controller<State> {

    private val gameStateController = GameController()
    private val menuStateController = MenuController()

    override fun bindState(state: State): View {
        this.state = state
        return when (state) {
            is GameState -> gameStateController.bindState(state)
            is MenuScreenState -> menuStateController.bindState(state)
            else -> error("Unknown state")
        }
    }

    override fun processEvent(event: Event): Message? {
        return when (state) {
            is GameState -> gameStateController.processEvent(event)
            is MenuScreenState -> menuStateController.processEvent(event)
            else -> error("Unknown state")
        }
    }
}