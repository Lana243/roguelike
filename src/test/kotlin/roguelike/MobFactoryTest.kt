package roguelike

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.mob.*
import roguelike.state.game.world.objects.units.mob.strategies.AggressiveStrategy
import roguelike.state.game.world.objects.units.mob.strategies.PassiveStrategy

class MobFactoryTest {
    @Test
    fun `Knight factory produces knights`() {
        val mobFactory = KnightFactory { AggressiveStrategy() }
        val mob = mobFactory.getMob(1, Position(0, 0))
        Assertions.assertTrue(mob is Knight)
        Assertions.assertTrue(mob.strategy is AggressiveStrategy)
    }

    @Test
    fun `Pawn factory produces pawns`() {
        val mobFactory = PawnFactory { PassiveStrategy() }
        val mob = mobFactory.getMob(1, Position(0, 0))
        Assertions.assertTrue(mob is Pawn)
        Assertions.assertTrue(mob.strategy is PassiveStrategy)
    }

    @Test
    fun `Mold factory produces molds`() {
        val mobFactory = MoldFactory()
        Assertions.assertTrue(mobFactory.getMob(1, Position(0, 0)) is Mold)
    }
}
