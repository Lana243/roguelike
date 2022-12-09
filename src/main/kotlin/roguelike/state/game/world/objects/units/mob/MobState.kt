package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.world.objects.units.AvoidanceStrategy
import roguelike.state.game.world.objects.units.MobStrategy

/**
 * Состояние моба.
 */
interface MobState {
    val strategy: MobStrategy
}

/**
 * Состояние, при котором у моба мало здоровья.
 */
class PoorHealthState : MobState {
    override val strategy: MobStrategy = AvoidanceStrategy()
}

/**
 * Состояние, при котором у моба всё хорошо.
 */
class GoodHealthState(
    initialStrategy: MobStrategy
) : MobState {
    override val strategy: MobStrategy = initialStrategy
}
