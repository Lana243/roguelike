package roguelike.state.game.world

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.world.map.Cell
import roguelike.state.game.world.map.GameMap
import roguelike.state.game.world.objects.Apple
import kotlin.math.abs

fun getDistance(p1: Position, p2: Position): Int = abs(p1.x - p2.x) + abs(p1.y - p2.y)

fun getCorrectMoves(
    position: Position,
    moves: List<MoveAction>,
    map: GameMap,
    passThroughUnits: Boolean = true
): List<MoveAction> =
    moves.filter { move ->
        val pos = position + move
        map.isInsideMap(pos) &&
            (map.getCell(pos) !is Cell.Solid ||
                map.getCell(pos) is Cell.Unit && passThroughUnits)
    }


fun World.getApplePositions(): Set<Position> =
    this.items.filter { (_, item) -> item is Apple }.keys
