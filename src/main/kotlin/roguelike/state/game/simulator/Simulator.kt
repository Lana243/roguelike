package roguelike.state.game.simulator

import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.GameUnit

interface Simulator {
    fun simulate(world: World, actions: (GameUnit) -> UnitAction): World
}

