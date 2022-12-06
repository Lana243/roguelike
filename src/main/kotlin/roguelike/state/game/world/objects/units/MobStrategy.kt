package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.map.Cell
import roguelike.state.game.world.World

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
        val targetPosition = world.player.position

        val preferredMoves = mutableListOf<MoveAction>()
        if (targetPosition.x < mob.position.x)
            preferredMoves += MoveAction(-1, 0)
        if (targetPosition.x > mob.position.x)
            preferredMoves += MoveAction(1, 0)
        if (targetPosition.y < mob.position.y)
            preferredMoves += MoveAction(0, -1)
        if (targetPosition.y > mob.position.y)
            preferredMoves += MoveAction(0, 1)
        preferredMoves += listOf(MoveAction(-1, 0), MoveAction(1, 0), MoveAction(0, -1), MoveAction(0, 1))

        for (move in preferredMoves) {
            val nextCell = world.map.getCell(mob.position + move)
            if (nextCell !is Cell.Solid || nextCell is Cell.Unit) {
                return move
            }
        }
        return Procrastinate
    }
}

/**
 * Моб старается избегать игрока.
 */
class AvoidanceStrategy : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        val antiTargetPosition = world.player.position

        val preferredMoves = mutableListOf<MoveAction>()
        if (antiTargetPosition.x < mob.position.x)
            preferredMoves += MoveAction(1, 0)
        if (antiTargetPosition.x > mob.position.x)
            preferredMoves += MoveAction(-1, 0)
        if (antiTargetPosition.y < mob.position.y)
            preferredMoves += MoveAction(0, 1)
        if (antiTargetPosition.y > mob.position.y)
            preferredMoves += MoveAction(0, -1)
        preferredMoves += listOf(MoveAction(-1, 0), MoveAction(1, 0), MoveAction(0, -1), MoveAction(0, 1))

        for (move in preferredMoves) {
            val nextCell = world.map.getCell(mob.position + move)
            if (nextCell !is Cell.Solid || nextCell is Cell.Unit) {
                return move
            }
        }
        return Procrastinate
    }
}

/**
 * Моб ходит в случайном направлении
 */
class ContusionStrategy(
    val baseStrategy: MobStrategy,
) : MobStrategy {
    override fun getNextAction(mob: Mob, world: World): UnitAction =
        listOf(MoveAction(-1, 0), MoveAction(1, 0), MoveAction(0, -1), MoveAction(0, 1)).random()
}