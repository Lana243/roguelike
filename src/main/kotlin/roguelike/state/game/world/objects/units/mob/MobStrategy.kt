package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.simulator.UnitSpecificAction
import roguelike.state.game.world.World
import roguelike.state.game.world.getApplePositions
import roguelike.state.game.world.getCorrectMoves
import roguelike.state.game.world.getDistance
import roguelike.state.game.world.map.findNextMove
import roguelike.state.game.world.objects.units.mob.Mob

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
        val move = findNextMove(mob.position, world.player.position, world.map, mob.moves, true)
            ?: return Procrastinate
        return move
    }
}

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


/**
 * Моб ходит в случайном направлении
 */
class ContusionStrategy(
    val baseStrategy: MobStrategy,
) : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction = mob.moves.random()
}

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

        val move = findNextMove(mob.position, nearest, world.map, mob.moves,)
            ?: return baseStrategy.getNextAction(mob, world)

        return move
    }
}


object Duplicate : UnitSpecificAction

/**
 * Специальная стратегия для плесени
 */
class MoldStrategy(
    private val baseStrategy: MobStrategy = AppleEatingStrategy(AggressiveStrategy())
) : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        if (mob.hp != mob.maxHp) {
            return baseStrategy.getNextAction(mob, world)
        } else {
            return Duplicate
        }
    }
}