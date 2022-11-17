package roguelike.state.game.world.objects.units

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.Sword
import kotlin.math.max
import kotlin.math.min


/**
 * Игрок
 */
data class PlayerUnit(
    override val id: Int,
    override var position: Position = Position(0, 0),
    var baseAttackRate: Int = 10,
    override var hp: Int = 50,
    override val maxHp: Int = 100,
    var level: Int = 1,
    val inventory: Inventory = Inventory(),
) : GameUnit() {

    fun updateHp(deltaHp: Int) {
        hp = max(0, min(maxHp, hp + deltaHp))
    }

    override val attackRate: Int
        get() {
            var attack = baseAttackRate
            for (it in inventory.items) {
                if (it.item is Sword && it.state == Inventory.ItemData.State.EQUIPED) {
                    attack += it.item.extraDamage
                }
            }
            return attack
        }
}