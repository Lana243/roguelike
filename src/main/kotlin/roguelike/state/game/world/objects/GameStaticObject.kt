package roguelike.state.game.world.objects

/**
 * Cтатические объекты
 */
sealed class GameStaticObject : GameObject()

/**
 * Колодец
 */
class Well(override val id: Int) : GameStaticObject() {
    var visited: Boolean = false
}

/**
 * Дверь. Попадая в нее, игрок проходит уровень
 */
class ExitDoor(override val id: Int) : GameStaticObject()
