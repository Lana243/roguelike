package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.PlayerUnit

class GameState : State() {
    val world: World = TODO()

    val player: PlayerUnit = TODO()

    override fun process(message: Message): State {
        TODO("Not yet implemented")
    }
}