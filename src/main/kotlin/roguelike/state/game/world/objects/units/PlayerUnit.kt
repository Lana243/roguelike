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
    var baseAttackRate: Int = 1,
    override var hp: Int = 5,
    override val maxHp: Int = 9,
    var level: Int = 1,
    var exp: Int = 0,
    val expToLevelUp: Int = 3,
    val inventory: Inventory = Inventory(),
) : GameUnit() {

    /**
     * Увеличивает уровень игрока: +1 к атаке, +1 к здоровью, опыт обнуляется
     */
    fun levelUp() {
        level++
        baseAttackRate++
        updateHp(1)
        exp = 0
    }

    fun updateExp() {
        exp++
        if (exp == expToLevelUp) {
            levelUp()
        }
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