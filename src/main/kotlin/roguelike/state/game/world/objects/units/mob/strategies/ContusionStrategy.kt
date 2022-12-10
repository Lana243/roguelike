package roguelike.state.game.world.objects.units.mob.strategies

import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.mob.Mob

/**
 * Моб ходит в случайном направлении
 */
class ContusionStrategy(
    val baseStrategy: MobStrategy,
) : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction = mob.moves.random()
}