package roguelike.state.game.world.objects.units.mob.strategies

import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.mob.Mob

abstract class StateBasedStrategy : MobStrategy {
    interface StrategyState {
        val strategy: MobStrategy
        fun next(mob: Mob, world: World): StrategyState
    }

    protected abstract fun initialState(): StrategyState

    private var state = initialState()


    override fun getNextAction(mob: Mob, world: World): UnitAction {
        state = state.next(mob, world)
        return state.strategy.getNextAction(mob, world)
    }
}