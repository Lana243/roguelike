package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.World
import roguelike.state.game.world.map.findNextMove
import roguelike.state.game.world.objects.units.mob.Mob

/**
 * Моб пытается найти игрока.
 */
class AggressiveStrategy : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        val move = findNextMove(mob.position, world.player.position, world.map, mob.moves, true)
            ?: return Procrastinate
        return move
    }
}