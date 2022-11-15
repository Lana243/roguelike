package roguelike.state.game.world.objects.units

import roguelike.state.game.world.objects.GameObject
import roguelike.state.game.world.Position

abstract class GameUnit : GameObject {
    abstract val position: Position
    abstract val attackRate: UInt
    abstract val maxHp: UInt
    abstract val hp: UInt
}