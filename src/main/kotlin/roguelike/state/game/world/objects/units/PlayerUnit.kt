package roguelike.state.game.world.objects.units

import roguelike.state.game.world.Position


class PlayerUnit(
    override val position: Position,
    override val attackRate: UInt,
    override val hp: UInt,
    override val maxHp: UInt,
    val exp: UInt,
    val level: UInt,
    val inventory: Inventory,
) : GameUnit()

data class Inventory(
    val itemIds: List<Int>
)