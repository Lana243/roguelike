package roguelike.state.menu

import roguelike.state.Message

/**
 * Высокоуровневое описание действий, совершаемых на экране меню
 */
sealed interface MenuMessage : Message {
    /**
     * Начать игру на сгенерированной уровне
     */
    object StartGameQuick : MenuMessage

    /**
     * Начать игру на уровне 1
     */
    object StartGameLevel1 : MenuMessage

    /**
     * Выйти с экрана меню
     */
    object Exit : MenuMessage
}

