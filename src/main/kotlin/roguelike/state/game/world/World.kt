package roguelike.state.game.world

import roguelike.state.game.world.objects.StaticObject
import roguelike.state.game.world.objects.units.Mob

class World {
    val map: GameMap = TODO()

    val staticObjects: Map<Int, StaticObject> = TODO()
    val mobs: Map<Int, Mob> = TODO()

    val tick: Int = 0
}