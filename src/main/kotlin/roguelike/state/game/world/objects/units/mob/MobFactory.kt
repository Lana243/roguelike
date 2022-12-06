package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.*

interface MobFactory {
    fun getMob(id: Int, position: Position): Mob
}

class PawnFactory(
    private val strategyFactory: StrategyFactory,
) : MobFactory {
    override fun getMob(id: Int, position: Position): Mob =
        Pawn(id, position, strategyFactory.getStrategy(position))
}


class KnightFactory(
    private val strategyFactory: StrategyFactory
) : MobFactory {
    override fun getMob(id: Int, position: Position): Mob =
        Knight(id, position, strategyFactory.getStrategy(position))
}

class MoldFactory(
) : MobFactory {
    override fun getMob(id: Int, position: Position): Mob {
        return Mold(id, position)
    }

}

class RandomMobFactory(
    private val factories: List<MobFactory>,
) : MobFactory {
    override fun getMob(id: Int, position: Position): Mob =
        factories.random().getMob(id, position)
}

fun defaultMobFactory(): MobFactory {
    val availableMobStrategies = listOf(
        PassiveStrategy(),
        AggressiveStrategy(),
        AvoidanceStrategy(),
    )


    val randomStrategyFactory = RandomStrategyFactory(availableMobStrategies)

    val availableMobFactories = listOf(
        PawnFactory(randomStrategyFactory),
        KnightFactory(randomStrategyFactory),
        MoldFactory()
    )

    return RandomMobFactory(availableMobFactories)
}

fun passiveMobFactory(): MobFactory {
    return PawnFactory(StrategyFactory { PassiveStrategy() })
}