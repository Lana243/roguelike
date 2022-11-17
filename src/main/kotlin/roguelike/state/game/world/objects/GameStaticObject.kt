package roguelike.state.game.world.objects

sealed class GameStaticObject : GameObject()

class Well(override val id: Int) : GameStaticObject() {
    val extraExp: UInt = 10U
}

class ExitDoor(override val id: Int) : GameStaticObject()
