package roguelike.state.game.world.objects.units

import roguelike.state.game.world.objects.GameObject
import roguelike.state.game.world.Position

abstract class GameUnit : GameObject {
    abstract val position: Position
    abstract val attackRate: Int
    abstract val maxHp: Int
    abstract val hp: Int
}