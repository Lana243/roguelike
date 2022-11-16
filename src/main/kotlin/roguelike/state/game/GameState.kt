package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.menu.MenuScreenState

class GameState : State() {
    val world: World = World()
    override fun process(message: Message): State {
        when (message) {
            is GameMessage -> {
                when (message) {
                    is GameMessage.PlayerActionMessage -> TODO()
                    GameMessage.Exit -> MenuScreenState()
                }
            }
            else -> error("Unknown message")
        }
        return this
    }
}
