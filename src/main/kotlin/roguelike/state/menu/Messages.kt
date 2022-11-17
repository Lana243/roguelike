package roguelike.state.menu

import roguelike.state.Message

/**
 * Высокоуровневое описание действий, совершаемых на экране меню
 */
sealed interface MenuMessage : Message {
    /**
     * Начать игру
     */
    object StartGame : MenuMessage

    /**
     * Выйти с экрана меню
     */
    object Exit : MenuMessage
}

