package roguelike.state.game.world.objects.units

import roguelike.state.game.world.Position
import kotlin.math.max
import kotlin.math.min


/**
 * Игрок
 */
data class PlayerUnit(
    override val id: Int,
    override var position: Position = Position(0, 0),
    override var attackRate: Int = 10,
    override var hp: Int = 50,
    override val maxHp: Int = 100,
    val exp: Int = 0,
    val level: Int = 1,
    val inventory: Inventory = Inventory(emptySet(), emptySet()),
) : GameUnit() {

    fun updateHp(deltaHp: Int) {
        hp = max(0, min(maxHp, hp + deltaHp))
    }
}

data class Inventory(
    val itemIds: Set<Int>,
    val equippedIds: Set<Int>,
)