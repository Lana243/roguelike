package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.world.Position

/**
 * Моб
 */
abstract class Mob : GameUnit() {
    /**
     * Стратегия движения моба
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
    override val moves: List<MoveAction> = listOf(
        MoveAction(-1, 0),
        MoveAction(1, 0),
        MoveAction(0, 1),
        MoveAction(0, -1)
    )
) : Mob()