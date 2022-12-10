package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.simulator.MobAction
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.SideMoves
import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.mob.strategies.AggressiveStrategy
import roguelike.state.game.world.objects.units.mob.strategies.AppleEatingStrategy
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.mob.strategies.MobStrategy
import roguelike.state.game.world.objects.units.mob.strategies.HpBasedStrategy

interface CloneableMob {
    fun clone(id: Int, position: Position): Mob
}

/**
 * Моб
 */
abstract class Mob : GameUnit() {
    /**
     * Начальная стратегия движения моба.
     */
    abstract var strategy: MobStrategy
}


/**
 * Моб пешка. Умеет передвигаться в клетку соседнюю по стороне.
 */
data class Pawn(
    override val id: Int,
    override var position: Position,
    override var strategy: MobStrategy,
    override val attackRate: Int = 2,
    override var maxHp: Int = 3,
    override var hp: Int = 3,
    override val moves: List<MoveAction> = SideMoves
) : Mob()

val KnightMoves = listOf(
    MoveAction(2, 1),
    MoveAction(-2, 1),
    MoveAction(2, -1),
    MoveAction(-2, -1),
    MoveAction(1, 2),
    MoveAction(1, -2),
    MoveAction(-1, 2),
    MoveAction(-1, -2)
)

/**
 * Моб конь. Умеет передвигаться как шахматный конь.
 */
data class Knight(
    override val id: Int,
    override var position: Position,
    override var strategy: MobStrategy,
    override val attackRate: Int = 2,
    override val maxHp: Int = 3,
    override var hp: Int = 3,
    override val moves: List<MoveAction> = KnightMoves
) : Mob()


/**
 * Моб плесень. Реплицируется, если у неё полное хп. Ищет яблочки. Если их нет, ищет игрока :))
 */
data class Mold(
    override val id: Int,
    override var position: Position,
    override val attackRate: Int = 1,
    override val maxHp: Int = 2,
    override var hp: Int = 1,
    override val moves: List<MoveAction> = SideMoves
) : Mob(), CloneableMob {

    override var strategy: MobStrategy = MoldStrategy

    override fun clone(id: Int, position: Position): Mold {
        return copy(id = id, position = position)
    }
}

object Clone : MobAction
val MoldStrategy = HpBasedStrategy(AppleEatingStrategy(AggressiveStrategy())) { _, _ -> Clone }