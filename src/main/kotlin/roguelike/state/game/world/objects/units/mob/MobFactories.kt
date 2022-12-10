package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.AggressiveStrategy
import roguelike.state.game.world.objects.units.AvoidanceStrategy
import roguelike.state.game.world.objects.units.PassiveStrategy
import roguelike.state.game.world.objects.units.StateStrategy

/**
 * Фабрика мобов.
 */
interface MobFactory {
    fun getMob(id: Int, position: Position): Mob
}

/**
 * Создаёт мобов типа [Pawn].
 */
class PawnFactory(
    private val strategyFactory: StrategyFactory,
) : MobFactory {
    override fun getMob(id: Int, position: Position): Mob =
        Pawn(id, position, strategyFactory.getStrategy(position))
}

/**
 * Создаёт мобов типа [Knight].
 */
class KnightFactory(
    private val strategyFactory: StrategyFactory
) : MobFactory {
    override fun getMob(id: Int, position: Position): Mob =
        Knight(id, position, strategyFactory.getStrategy(position))
}

/**
 * Создаё мобов типа [Mold].
 */
class MoldFactory : MobFactory {
    override fun getMob(id: Int, position: Position): Mob =
        Mold(id, position)
}

/**
 * Возвращает случайного моба, которого выдала одна из фабрик [factories].
 */
class RandomMobFactory(
    private val factories: List<MobFactory>,
) : MobFactory {
    override fun getMob(id: Int, position: Position): Mob =
        factories.random().getMob(id, position)
}

fun defaultMobFactory(): MobFactory {
    val availableMobStrategies = listOf(
        StrategyFactory { PassiveStrategy() },
        StrategyFactory { AggressiveStrategy() },
        StrategyFactory { AvoidanceStrategy() },
        StrategyFactory { StateStrategy(AvoidanceStrategy(), AggressiveStrategy()) }

    )

    val randomStrategyFactory = RandomStrategyFactory(availableMobStrategies)

    val availableMobFactories = listOf(
        PawnFactory(randomStrategyFactory),
        KnightFactory(randomStrategyFactory),
        MoldFactory()
    )

    return RandomMobFactory(availableMobFactories)
}

fun passiveMobFactory(): MobFactory =
    PawnFactory { PassiveStrategy() }