package roguelike.state

// состояние игры
abstract class State {
    // обработка пользовательского сообщения, после чего возвращается новое актуальное состояние
    abstract fun process(message: Message): State
}