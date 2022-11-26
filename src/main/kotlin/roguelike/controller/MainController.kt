package roguelike.controller

import roguelike.controller.defeat.DefeatController
import roguelike.controller.game.GameController
import roguelike.controller.menu.MenuController
import roguelike.controller.victory.VictoryController
import roguelike.state.Message
import roguelike.state.State
import roguelike.state.defeat.DefeatScreenState
import roguelike.state.game.GameState
import roguelike.state.menu.MenuScreenState
import roguelike.state.victory.VictoryScreenState
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
    private val victoryScreenState = VictoryController()
    private val defeatScreenState = DefeatController()

    override fun bindState(state: State): View {
        this.state = state
        return when (state) {
            is GameState -> gameStateController.bindState(state)
            is MenuScreenState -> menuStateController.bindState(state)
            is VictoryScreenState -> victoryScreenState.bindState(state)
            is DefeatScreenState -> defeatScreenState.bindState(state)
            else -> error("Unknown state")
        }
    }

    override fun processEvent(event: Event): Message? {
        return when (state) {
            is GameState -> gameStateController.processEvent(event)
            is MenuScreenState -> menuStateController.processEvent(event)
            is VictoryScreenState -> victoryScreenState.processEvent(event)
            is DefeatScreenState -> defeatScreenState.processEvent(event)
            else -> error("Unknown state")
        }
    }
}