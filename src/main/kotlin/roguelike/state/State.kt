package roguelike.state

abstract class State {
    abstract fun process(message: Message): State
    abstract fun <T> accept(visitor: StateVisitor<T>): T
}