package roguelike.state.victory

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.menu.MenuScreenState
import kotlin.system.exitProcess

class VictoryScreenState : State() {
    val wonText = "You won!"
    val menuButtonText = "Press <F1> to exit to the main menu"
    val escButtonText = "Press <Esc> to exit the game"

    val screenLengthX = 80
    val screenLengthY = 24

    override fun process(message: Message): State =
        when (message) {
            is VictoryMessage -> when (message) {
                VictoryMessage.MainMenu -> MenuScreenState()
                VictoryMessage.Exit -> exitProcess(0)
            }
            else -> error("Unknown message")
        }
}