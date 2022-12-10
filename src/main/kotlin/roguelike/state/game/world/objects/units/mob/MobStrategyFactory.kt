package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.mob.strategies.MobStrategy

/**
 * Фабрика стратегий мобов.
 */
fun interface StrategyFactory {
    fun getStrategy(position: Position): MobStrategy
}

/**
 * Выдаёт случайную стратегию из списка [strategies].
 */
class RandomStrategyFactory(
    private val strategies: List<StrategyFactory>
) : StrategyFactory {
    override fun getStrategy(position: Position): MobStrategy {
        return strategies.random().getStrategy(position)
    }
}
