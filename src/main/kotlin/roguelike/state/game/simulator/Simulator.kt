package roguelike.state.game.simulator

import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.GameUnit

interface Simulator {
    /**
     * Применение [actions] к [world] -- текущему состоянию игрового мира.
     */
    fun simulate(world: World, actions: Map<GameUnit, (World) -> UnitAction>)
}

