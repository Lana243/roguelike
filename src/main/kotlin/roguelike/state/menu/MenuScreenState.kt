package roguelike.state.menu

import roguelike.state.Message
import roguelike.state.State

class MenuScreenState : State() {
    override fun process(message: Message): State {
        when (message) {
            is MenuMessage -> when (message) {
                MenuMessage.StartGame -> TODO()
                MenuMessage.Exit -> TODO()
            }
            else -> error("Unknown message")
        }
    }
}