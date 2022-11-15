package roguelike.state.game

import roguelike.state.Message
import roguelike.state.game.simulator.UnitAction

sealed interface GameMessage : Message {
    data class PlayerActionMessage(
        val action: UnitAction
    ) : GameMessage

    object Exit : GameMessage
}