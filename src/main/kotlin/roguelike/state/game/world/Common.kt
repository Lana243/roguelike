package roguelike.state.game.world

import roguelike.state.game.simulator.MoveAction

/**
 * Позиция на карте
 */
data class Position(
    val x: Int,
    val y: Int
) {
    /**
     * Возвращает позицию, полученную из текущей осуществлением [MoveAction]
     */
    operator fun plus(move: MoveAction): Position =
        copy(x = x + move.dx, y = y + move.dy)
}