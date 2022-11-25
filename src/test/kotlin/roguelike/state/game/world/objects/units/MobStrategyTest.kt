package roguelike.state.game.world.objects.units

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.world.*

class MobStrategyTest {

    @Test
    fun `passive strategy should do nothing`() {
        val nextAction = PassiveStrategy().getNextAction(mob, world)
        assertEquals(nextAction, Procrastinate)
    }

    @Test
    fun `aggressive strategy should move to the player`() {
        val nextAction = AggressiveStrategy().getNextAction(mob, world)
        assert(nextAction in listOf(MoveAction.LEFT, MoveAction.UP))
    }

    @Test
    fun `avoidance strategy should be to go against the player`() {
        val nextAction = AvoidanceStrategy().getNextAction(mob, world)
        assert(nextAction in listOf(MoveAction.RIGHT, MoveAction.DOWN))
    }

    // internal

    private val mob = Mob(2, Position(10, 10), PassiveStrategy())

    private val mapFactory = MapFromFileGenerator("src/test/resources/test-map.txt")

    private val world = WorldFactory(mapFactory).createWorld()
}