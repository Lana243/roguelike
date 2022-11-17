package roguelike.controller.game

import roguelike.controller.Controller
import roguelike.state.Message
import roguelike.state.game.GameMessage
import roguelike.state.game.GameState
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.UnitAction
import roguelike.ui.Event
import roguelike.ui.views.View


/**
 * Контроллер, обеспечивающий взаимодействие UI и игровой логики, когда пользователь находится на экране уровня
 */
class GameController : Controller<GameState> {

    override fun bindState(state: GameState): View {
        return gameViewBuilder.build(state)
    }

    override fun processEvent(event: Event): Message? {
        return when (event) {
            Event.KeyLeftPressed -> GameMessage.PlayerActionMessage(MoveAction.LEFT)
            Event.KeyRightPressed -> GameMessage.PlayerActionMessage(MoveAction.RIGHT)
            Event.KeyUpPressed -> GameMessage.PlayerActionMessage(MoveAction.UP)
            Event.KeyDownPressed -> GameMessage.PlayerActionMessage(MoveAction.DOWN)
            Event.KeyEscPressed -> GameMessage.Exit
            else -> null
        }
    }

    // internal

    private val gameViewBuilder = GameViewBuilder()
}