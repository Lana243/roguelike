package roguelike.state

interface StateVisitor<T> {
    fun accept(): T
}