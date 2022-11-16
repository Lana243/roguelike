package roguelike.state.menu

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.GameState
import kotlin.system.exitProcess

class MenuScreenState : State() {
    override fun process(message: Message): State =
        when (message) {
            is MenuMessage -> when (message) {
                MenuMessage.StartGame -> GameState()
                MenuMessage.Exit -> exitProcess(0)
            }
            else -> error("Unknown message")
        }
}