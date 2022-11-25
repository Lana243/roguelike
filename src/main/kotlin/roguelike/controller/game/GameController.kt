package roguelike.controller.game

import roguelike.controller.Controller
import roguelike.state.Message
import roguelike.state.game.GameMessage
import roguelike.state.game.GameState
import roguelike.state.game.simulator.ToggleInventoryItem
import roguelike.state.game.simulator.Interact
import roguelike.state.game.simulator.MoveAction
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
            is Event.LetterOrDigitKeyPressed -> when (event.char) {
                'q' -> GameMessage.ShowMobsHp
                'w' -> GameMessage.ShowMobsAttackRate
                'e' -> GameMessage.PlayerActionMessage(Interact)
                '1' -> GameMessage.PlayerActionMessage(ToggleInventoryItem(0))
                '2' -> GameMessage.PlayerActionMessage(ToggleInventoryItem(1))
                '3' -> GameMessage.PlayerActionMessage(ToggleInventoryItem(2))
                '4' -> GameMessage.PlayerActionMessage(ToggleInventoryItem(3))
                '5' -> GameMessage.PlayerActionMessage(ToggleInventoryItem(4))
                else -> null
            }
            else -> null
        }
    }

    // internal

    private val gameViewBuilder = GameViewBuilder()
}