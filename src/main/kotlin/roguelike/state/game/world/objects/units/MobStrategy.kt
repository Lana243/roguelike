package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.map.Cell
import roguelike.state.game.world.World
import roguelike.state.game.world.getDistance

/**
 * Стратегия поведения моба в зависимости от положения в игре.
 */
interface MobStrategy {
    fun getNextAction(mob: Mob, world: World): UnitAction
}

/**
 * Моб стоит и ничего не делает.
 */
class PassiveStrategy : MobStrategy {
    override fun getNextAction(mob: Mob, world: World) = Procrastinate
}

/**
 * Моб пытается найти игрока.
 */
class AggressiveStrategy : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        val moves = mob.moves.map { it to getDistance(world, mob.position + it, world.player.position) }
        return moves.minByOrNull { it.second }?.first ?: Procrastinate
    }
}

/**
 * Моб старается избегать игрока.
 */
class AvoidanceStrategy : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        val moves = mob.moves.map { it to getDistance(world, mob.position + it, world.player.position) }
        return moves.maxByOrNull { it.second }?.first ?: Procrastinate
    }
}

/**
 * Моб ходит в случайном направлении
 */
class ContusionStrategy(
    val baseStrategy: MobStrategy,
) : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction = mob.moves.random()
}