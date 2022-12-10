package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.World
import roguelike.state.game.world.getCorrectMoves
import roguelike.state.game.world.getDistance
import roguelike.state.game.world.objects.units.mob.Mob

/**
 * Моб старается избегать игрока.
 */
class AvoidanceStrategy : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        val moves = getCorrectMoves(mob.position, mob.moves, world.map).map {
            it to getDistance(mob.position + it, world.player.position)
        }
        return moves.maxByOrNull { it.second }?.first ?: Procrastinate
    }
}