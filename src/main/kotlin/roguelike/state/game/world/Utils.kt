package roguelike.state.game.world

import kotlin.math.abs

fun getDistance(world: World, p1: Position, p2: Position): Int = abs(p1.x - p2.x) + abs(p1.y - p2.y)
