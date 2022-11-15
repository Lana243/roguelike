package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.PlayerUnit

class GameState : State() {
    val world: World = World()

    val player: PlayerUnit = PlayerUnit()

    override fun process(message: Message): State {
        when (message) {
            is GameMessage -> {
                when (message) {
                    is GameMessage.PlayerActionMessage -> {
                        player.process(message)
                    }
                    GameMessage.Exit -> TODO()
                }
            }
            else -> error("Unknown message")
        }
        return this
    }
}
