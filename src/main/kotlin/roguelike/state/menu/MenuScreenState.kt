package roguelike.state.menu

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.GameState
import kotlin.system.exitProcess

/**
 * состояние экрана меню
 */
class MenuScreenState : State() {

    val quickPlayButtonText = "Press <Enter> to Quick Play"
    val level1ButtonText = "Press <F1> to start Level 1"
    val escButtonText = "Press <Esc> to exit"

    val screenLengthX = 80
    val screenLengthY = 24

    override fun process(message: Message): State =
        when (message) {
            is MenuMessage -> when (message) {
                MenuMessage.StartGameQuick -> GameState.create(MenuMessage.StartGameQuick)
                MenuMessage.StartGameLevel1 -> GameState.create(MenuMessage.StartGameLevel1)
                MenuMessage.Exit -> exitProcess(0)
            }
            else -> error("Unknown message")
        }
}