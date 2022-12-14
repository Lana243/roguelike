package roguelike.state.menu

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.GameState
import kotlin.system.exitProcess

/**
 * Состояние экрана меню.
 */
class MenuScreenState : State() {

    val quickPlayButtonText = "Press <Enter> to Quick Play"
    val level1ButtonText = "Press <F1> to start Level 1"
    val level2ButtonText = "Press <F2> to start Level 2"
    val escButtonText = "Press <Esc> to exit"

    override fun process(message: Message): State =
        when (message) {
            is MenuMessage -> when (message) {
                MenuMessage.StartGameQuick -> GameState.create(MenuMessage.StartGameQuick)
                MenuMessage.StartGameLevel1 -> GameState.create(MenuMessage.StartGameLevel1)
                MenuMessage.StartGameLevel2 -> GameState.create(MenuMessage.StartGameLevel2)
                MenuMessage.Exit -> exitProcess(0)
            }
            else -> error("Unknown message")
        }
}