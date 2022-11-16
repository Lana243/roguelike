package roguelike.state.game.world.objects

import roguelike.state.game.world.objects.GameObject

sealed class StaticObject : GameObject

object Well : StaticObject() {
    val extraExp: UInt = 10U
}

