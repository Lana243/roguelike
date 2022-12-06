package roguelike.state.game.simulator

sealed interface UnitAction

/**
 * Класс, описывающий движение игрока в игровом мире
 */
data class MoveAction(
    val dx: Int,
    val dy: Int,
) : UnitAction

/**
 * Класс, описывающий действие игрока: надеть вещь из инвентаря
 */
class ToggleInventoryItem(val pos: Int) : UnitAction

object Interact : UnitAction

/**
 * Класс, описывающий действие: не делать ничего
 */
object Procrastinate : UnitAction
