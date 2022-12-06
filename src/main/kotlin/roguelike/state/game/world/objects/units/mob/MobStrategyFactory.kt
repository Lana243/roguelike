package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.MobStrategy

fun interface StrategyFactory {
    fun getStrategy(position: Position): MobStrategy
}

class RandomStrategyFactory(
    val strategies: List<MobStrategy>
) : StrategyFactory {
    override fun getStrategy(position: Position): MobStrategy {
        return strategies.random()
    }
}
