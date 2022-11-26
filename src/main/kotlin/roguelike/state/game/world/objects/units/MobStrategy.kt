package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.Cell
import roguelike.state.game.world.World

/**
 * Стратегия поведения моба.
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
            preferredMoves += MoveAction.LEFT
        if (targetPosition.x > mob.position.x)
            preferredMoves += MoveAction.RIGHT
        if (targetPosition.y < mob.position.y)
            preferredMoves += MoveAction.UP
        if (targetPosition.y > mob.position.y)
            preferredMoves += MoveAction.DOWN
        preferredMoves += listOf(MoveAction.LEFT, MoveAction.RIGHT, MoveAction.UP, MoveAction.DOWN)

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
            preferredMoves += MoveAction.RIGHT
        if (antiTargetPosition.x > mob.position.x)
            preferredMoves += MoveAction.LEFT
        if (antiTargetPosition.y < mob.position.y)
            preferredMoves += MoveAction.DOWN
        if (antiTargetPosition.y > mob.position.y)
            preferredMoves += MoveAction.UP
        preferredMoves += listOf(MoveAction.LEFT, MoveAction.RIGHT, MoveAction.UP, MoveAction.DOWN)

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
        MoveAction.values().random()
}