package roguelike.state.game

import roguelike.state.Message
import roguelike.state.game.simulator.UnitAction

/**
 * Высокоуровневое описание действий, относящихся к самому игровому процессу
 */
sealed interface GameMessage : Message {
    /**
     * Действия, совершенные игроком
     */
    data class PlayerActionMessage(
        val action: UnitAction
    ) : GameMessage

    /**
     * Выход c экрана игры
     */
    object Exit : GameMessage
}