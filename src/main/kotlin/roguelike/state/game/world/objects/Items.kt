package roguelike.state.game.world.objects

/**
 * Вещи, которые могут быть в игре. Обладают различными свойствами.
 * Могут быть, как одноразовыми, так и помещаться в инвентарь
 */
sealed class GameItem : GameObject()

/**
 * Меч. Может лежать в инвентаре
 */
class Sword(override val id: Int) : GameItem() {
    val extraDamage: Int = 1
}

/**
 * Яблоко. Поднимает здоровья игрока
 */
class Apple(override val id: Int) : GameItem() {
    val healsHp: Int = 1
}