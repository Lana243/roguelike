package roguelike.state.game.world

import roguelike.state.game.world.objects.Item
import roguelike.state.game.world.objects.StaticObject
import roguelike.state.game.world.objects.units.PlayerUnit

class World {
    val map: GameMap = GameMapImpl(TODO())

    val player: PlayerUnit = PlayerUnit()

    val staticObjects: Map<Int, StaticObject> = mutableMapOf()
    val items: Map<Int, Item> = mutableMapOf()

    val tick: Int = 0
}