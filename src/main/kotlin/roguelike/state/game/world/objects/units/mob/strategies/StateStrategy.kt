package roguelike.state.game.world.objects.units.mob.strategies

import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.mob.Mob

class StateStrategy(
    strategyOnPoorHp: MobStrategy,
    strategyOnGoodHp: MobStrategy
) : MobStrategy {
    interface MobState {
        val strategy: MobStrategy
        fun next(mob: Mob, world: World): MobState
    }


    /**
     * Состояние, при котором у моба мало здоровья.
     */
    private val poorState: MobState = object : MobState {
        override val strategy: MobStrategy = strategyOnPoorHp

        override fun next(mob: Mob, world: World): MobState =
            if (mob.hp != mob.maxHp) {
                this
            } else {
                goodState
            }
    }

    /**
     * Состояние, при котором у моба всё хорошо.
     */
    private val goodState: MobState = object : MobState {
        override val strategy: MobStrategy = strategyOnGoodHp

        override fun next(mob: Mob, world: World): MobState =
            if (mob.hp == mob.maxHp) {
                this
            } else {
                poorState
            }
    }

    /**
     * Состояние моба.
     */
    var state = goodState
    override fun getNextAction(mob: Mob, world: World): UnitAction {
        state = state.next(mob, world)
        return state.strategy.getNextAction(mob, world)
    }

    fun isInGoodState() = state === goodState
}