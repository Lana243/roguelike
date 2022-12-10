package roguelike.state.game.world.objects.units.mob.strategies

import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.mob.Mob

/**
 * Моб стоит и ничего не делает.
 */
class PassiveStrategy : MobStrategy {
    override fun getNextAction(mob: Mob, world: World) = Procrastinate
}