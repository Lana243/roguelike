package roguelike.state.defeat

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.menu.MenuScreenState
import kotlin.system.exitProcess

class DefeatScreenState : State() {
    val defeatText = "Defeated!"
    val menuButtonText = "Press <Enter> to exit to the main menu"
    val escButtonText = "Press <Esc> to exit the game"

    override fun process(message: Message): State =
        when (message) {
            is DefeatMessage -> when (message) {
                DefeatMessage.MainMenu -> MenuScreenState()
                DefeatMessage.Exit -> exitProcess(0)
            }
            else -> error("Unknown message")
        }
}