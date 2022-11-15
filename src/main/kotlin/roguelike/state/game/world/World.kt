package roguelike.state.game.world

import roguelike.state.Message
import roguelike.state.game.GameMessage
import roguelike.state.game.world.objects.StaticObject
import roguelike.state.game.world.objects.units.Mob

class World {
    val map: GameMap = GameMapImpl()

    val staticObjects: Map<Int, StaticObject> = emptyMap()
    val mobs: Map<Int, Mob> = emptyMap()

    val tick: Int = 0
}