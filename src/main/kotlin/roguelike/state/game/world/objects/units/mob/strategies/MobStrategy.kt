package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.mob.Mob

/**
 * Стратегия поведения моба в зависимости от положения в игре.
 */
fun interface MobStrategy {
    fun getNextAction(mob: Mob, world: World): UnitAction
}