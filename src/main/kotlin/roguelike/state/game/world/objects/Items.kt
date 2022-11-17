package roguelike.state.game.world.objects

sealed class GameItem : GameObject

object Sword : GameItem() {
    val extraDamage: Int = 10
}

object Apple : GameItem() {
    val healsHp: Int = 10
}