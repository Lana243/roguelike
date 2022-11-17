package roguelike.state.menu

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.GameState
import kotlin.system.exitProcess

/**
 * состояние экрана меню
 */
class MenuScreenState : State() {

    val playButtonText = "Press <Enter> to play"
    val escButtonText = "Press <Esc> to exit"

    val screenLengthX = 80
    val screenLengthY = 24

    override fun process(message: Message): State =
        when (message) {
            is MenuMessage -> when (message) {
                MenuMessage.StartGame -> GameState()
                MenuMessage.Exit -> exitProcess(0)
            }
            else -> error("Unknown message")
        }
}