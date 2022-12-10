package roguelike.state.game.world.objects.units.mob.strategies

import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.World
import roguelike.state.game.world.getApplePositions
import roguelike.state.game.world.getDistance
import roguelike.state.game.world.map.findNextMove
import roguelike.state.game.world.objects.units.mob.Mob

/**
 * Моб хочет скушать ближайшее яблочко
 */
class AppleEatingStrategy(
    private val baseStrategy: MobStrategy = PassiveStrategy(),
) : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        val apples = world.getApplePositions()

        val nearest = apples.minByOrNull { getDistance(mob.position, it) }
            ?: return baseStrategy.getNextAction(mob, world)

        val move = findNextMove(mob.position, nearest, world.map, mob.moves)
            ?: return baseStrategy.getNextAction(mob, world)

        return move
    }
}