package roguelike.state.game.world

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.world.map.Cell
import roguelike.state.game.world.objects.units.GameUnit
import kotlin.math.abs

fun getDistance(world: World, p1: Position, p2: Position): Int = abs(p1.x - p2.x) + abs(p1.y - p2.y)

fun getCorrectMoves(unit: GameUnit, world: World): List<MoveAction> =
    unit.moves.filter { world.map.isInsideMap(unit.position + it) }
        .filter { world.map.getCell(unit.position + it) !is Cell.Solid || world.map.getCell(unit.position + it) is Cell.Unit }

