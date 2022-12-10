package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.SideMoves
import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.MobStrategy
import roguelike.state.game.world.objects.units.MoldStrategy

/**
 * Моб
 */
abstract class Mob : GameUnit() {

    /**
     * Количество здоровья, при котором состояние меняется на [PoorHealthState].
     */
    open val poorHealthThreshold = 1

    /**
     * Начальная стратегия движения моба.
     */
    abstract val initialStrategy: MobStrategy

    /**
     * Текущее состояние моба.
     */
    abstract var state: MobState

    override fun updateHp(deltaHp: Int) {
        super.updateHp(deltaHp)
        state = if (hp <= poorHealthThreshold) {
            PoorHealthState()
        } else {
            GoodHealthState(initialStrategy)
        }
    }
}


/**
 * Моб пешка. Умеет передвигаться в клетку соседнюю по стороне.
 */
data class Pawn(
    override val id: Int,
    override var position: Position,
    override val initialStrategy: MobStrategy,
    override val attackRate: Int = 2,
    override var maxHp: Int = 3,
    override var hp: Int = 3,
    override val moves: List<MoveAction> = SideMoves
) : Mob() {
    override var state: MobState = GoodHealthState(initialStrategy)
}

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
    override val initialStrategy: MobStrategy,
    override val attackRate: Int = 2,
    override val maxHp: Int = 3,
    override var hp: Int = 3,
    override val moves: List<MoveAction> = KnightMoves
) : Mob() {
    override var state: MobState = GoodHealthState(initialStrategy)
}

interface Cloneable<T> {
    fun clone(): T
}

data class Mold(
    override val id: Int,
    override var position: Position,
    override val attackRate: Int = 1,
    override val maxHp: Int = 2,
    override var hp: Int = 1,
    override val moves: List<MoveAction> = SideMoves
) : Mob(), Cloneable<Mold?> {

    override val poorHealthThreshold = 0

    override val initialStrategy: MobStrategy = MoldStrategy()

    override var state: MobState = GoodHealthState(initialStrategy)

    override fun clone(): Mold? {
        if (hp != maxHp) {
            return null
        }
        hp /= 2
        return copy(hp = hp)
    }
}
