package roguelike.state.game.world.objects.units

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.world.*
import roguelike.state.game.world.map.MapBuilder
import roguelike.state.game.world.objects.units.mob.Pawn
import roguelike.state.game.world.objects.units.mob.strategies.AggressiveStrategy
import roguelike.state.game.world.objects.units.mob.strategies.AvoidanceStrategy
import roguelike.state.game.world.objects.units.mob.strategies.PassiveStrategy

class MobStrategyTest {

    @Test
    fun `passive strategy should do nothing`() {
        val nextAction = PassiveStrategy().getNextAction(mob, world)
        assertEquals(nextAction, Procrastinate)
    }

    @Test
    fun `aggressive strategy should move to the player`() {
        val nextAction = AggressiveStrategy().getNextAction(mob, world)
        assert(nextAction in listOf(MoveAction(-1, 0), MoveAction(0, -1)))
    }

    @Test
    fun `avoidance strategy should be to go against the player`() {
        val nextAction = AvoidanceStrategy().getNextAction(mob, world)
        assert(nextAction in listOf(MoveAction(1, 0), MoveAction(0, 1)))
    }

    // internal

    private val mob = Pawn(2, Position(4, 3), PassiveStrategy())

    val mapBuilder = MapBuilder().fromFile("src/test/resources/test-map.txt")

    private val world = WorldFactory(mapBuilder).createWorld()
}