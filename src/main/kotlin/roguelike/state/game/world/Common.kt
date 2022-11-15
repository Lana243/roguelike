package roguelike.state.game.world

import roguelike.state.game.simulator.MoveAction

data class Position(
    val x: Int,
    val y: Int
) {
    operator fun plus(move: MoveAction): Position =
        copy(x = x + move.dx, y = y + move.dy)
}