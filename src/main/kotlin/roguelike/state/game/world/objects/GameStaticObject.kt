package roguelike.state.game.world.objects

sealed class GameStaticObject : GameObject

object Well : GameStaticObject() {
    val extraExp: UInt = 10U
}

