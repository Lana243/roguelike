package roguelike.state.game.simulator

import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.GameUnit

interface Simulator {
    /**
     * Применение [actions] к [world] -- текущему состоянию игрового мира.
     * Возвращается новый [World] -- новое состояние игрового мира.
     */
    fun simulate(world: World, actions: (GameUnit) -> UnitAction): World
}

