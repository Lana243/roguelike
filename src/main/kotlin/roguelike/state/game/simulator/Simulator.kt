package roguelike.state.game.simulator

import roguelike.state.game.world.World

interface Simulator {
    fun simulate(world: World, actions: (Unit) -> UnitAction): World
}