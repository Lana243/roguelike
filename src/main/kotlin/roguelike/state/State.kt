package roguelike.state

abstract class State {
    abstract fun process(message: Message): State
}