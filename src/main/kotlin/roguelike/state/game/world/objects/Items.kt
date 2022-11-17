package roguelike.state.game.world.objects

sealed class GameItem : GameObject()

class Sword(override val id: Int) : GameItem() {
    val extraDamage: Int = 10
}

class Apple(override val id: Int) : GameItem() {
    val healsHp: Int = 10
}