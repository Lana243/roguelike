package roguelike.state.game.world.objects

sealed class Item : GameObject

object Sword : Item() {
    val extraDamage: Int = 10
}

object Apple : Item() {
    val healsHp: Int = 10
}