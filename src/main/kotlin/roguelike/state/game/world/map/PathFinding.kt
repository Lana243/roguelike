package roguelike.state.game.world.map

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.world.Position
import roguelike.state.game.world.getCorrectMoves

/**
 * Ищет кратчайший путь между двумя точками на карте.
 */
fun findNextMove(
    position: Position,
    targetPosition: Position,
    map: GameMap,
    moves: List<MoveAction>,
    passThroughUnits: Boolean = false,
): MoveAction? {
    val prv = mutableMapOf<Position, MoveAction>()
    val queue = ArrayDeque<Position>()
    queue.addLast(position)

    while (queue.isNotEmpty()) {
        val u = queue.first()
        queue.removeFirst()

        val positions = getCorrectMoves(u, moves, map).map { u + it to it }
            .filter { map.getCell(it.first) !is Cell.Unit || passThroughUnits || it.first == targetPosition }

        for ((v, mv) in positions) {
            if (v !in prv.keys) {
                prv[v] = prv.getOrDefault(u, mv)
                queue.addLast(v)
            }

        }
    }

    return prv[targetPosition]
}