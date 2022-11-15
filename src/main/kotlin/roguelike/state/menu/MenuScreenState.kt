package roguelike.state.menu

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.StateVisitor

class MenuScreenState : State() {
    override fun process(message: Message): State {
        TODO("Not yet implemented")
    }

    override fun <T> accept(visitor: StateVisitor<T>): T {
        TODO("Not yet implemented")
    }
}