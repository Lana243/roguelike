package roguelike.state.game.world.map

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.world.Position
import roguelike.state.game.world.getCorrectMoves

fun findNextMove(
    position: Position,
    targetPosition: Position,
    map: GameMap,
    moves: List<MoveAction>,
    passThroughUnits: Boolean = false
): MoveAction? {
    val prv = mutableMapOf<Position, MoveAction>()
    val queue = ArrayDeque<Position>()
    queue.addLast(position)

    while (queue.isNotEmpty()) {
        val u = queue.first()
        queue.removeFirst()

        val positions = getCorrectMoves(u, moves, map, passThroughUnits).map { u + it to it }

        for ((v, mv) in positions) {
            if (v !in prv.keys) {
                prv[v] = prv.getOrDefault(u, mv)
                queue.addLast(v)
            }

        }
    }

    return prv[targetPosition]
}