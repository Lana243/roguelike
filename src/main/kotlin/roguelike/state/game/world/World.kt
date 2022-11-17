package roguelike.state.game.world

import roguelike.state.game.world.objects.GameItem
import roguelike.state.game.world.objects.GameStaticObject
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.utility.IdManager

data class World(
    val map: GameMap,
    val player: PlayerUnit,
    val staticObjects: Map<Int, GameStaticObject>,
    val items: Map<Int, GameItem>,
    val idManager: IdManager = IdManager()
) {
    val tick: Int = 0
}
