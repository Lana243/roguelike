package roguelike.state.game.world.objects.units

import roguelike.state.game.world.Position

data class PlayerUnit(
    override var position: Position = Position(0, 0),
    override val attackRate: Int = 10,
    override val hp: Int = 100,
    override val maxHp: Int = 100,
    val exp: Int = 0,
    val level: Int = 1,
    val inventory: Inventory = Inventory(emptySet(), emptySet()),
) : GameUnit()

data class Inventory(
    val itemIds: Set<Int>,
    val equippedIds: Set<Int>,
)