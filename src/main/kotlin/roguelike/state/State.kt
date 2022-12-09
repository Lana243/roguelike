package roguelike.state

/**
 * Состояние игры.
 */
abstract class State {
    /**
     * Обработка пользовательского сообщения, после чего возвращается новое актуальное состояние.
     */
    abstract fun process(message: Message): State
}