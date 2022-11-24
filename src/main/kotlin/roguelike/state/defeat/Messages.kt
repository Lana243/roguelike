package roguelike.state.defeat

import roguelike.state.Message

/**
 * Высокоуровневое описание действий, совершаемых на экране поражения
 */
sealed interface DefeatMessage : Message {
    /**
     * Выйти из игры
     */
    object Exit : DefeatMessage

    /**
     * Главное меню
     */
    object MainMenu : DefeatMessage
}

