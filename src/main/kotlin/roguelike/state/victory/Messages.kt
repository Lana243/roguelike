package roguelike.state.victory

import roguelike.state.Message

/**
 * Высокоуровневое описание действий, совершаемых на экране победы
 */
sealed interface VictoryMessage : Message {
    /**
     * Выйти из игры
     */
    object Exit : VictoryMessage

    /**
     * Главное меню
     */
    object MainMenu : VictoryMessage
}

