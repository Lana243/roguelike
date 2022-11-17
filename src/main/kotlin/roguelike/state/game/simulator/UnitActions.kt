package roguelike.state.game.simulator

sealed interface UnitAction

/**
 * Класс, описывающий движение игрока в игровом мире
 */
enum class MoveAction(
    val dx: Int,
    val dy: Int,
) : UnitAction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    DOWN(0, 1),
    UP(0, -1),
}

/**
 * Класс, описывающий действие игрока: надеть вещь из инвентаря
 */
class Equip(val itemId: Int) : UnitAction

/**
 * Класс описывающий действие игрока: снять надетую вещь и переместить в интвентарь
 */
class Unequip(val itemId: Int) : UnitAction

object Interact : UnitAction