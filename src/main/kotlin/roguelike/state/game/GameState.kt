package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.StateVisitor
import roguelike.state.game.world.World

class GameState : State() {
    val world: World = TODO()


    override fun process(message: Message): State {
        TODO("Not yet implemented")
    }

    override fun <T> accept(visitor: StateVisitor<T>): T {
        TODO("Not yet implemented")
    }
}