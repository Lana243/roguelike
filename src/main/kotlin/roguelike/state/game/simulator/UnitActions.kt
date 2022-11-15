package roguelike.state.game.simulator

sealed interface UnitAction

enum class MoveAction(
    val dx: Int,
    val dy: Int,
) : UnitAction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    DOWN(0, -1),
    UP(0, 1),
}

object Interact : UnitAction