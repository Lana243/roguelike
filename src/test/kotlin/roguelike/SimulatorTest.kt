package roguelike

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.world.MapFromFileGenerator
import roguelike.state.game.world.Position
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.objects.units.PlayerUnit

class SimulatorTest {
    @Test
    fun `If player moves to the wall, his position does not change`() {
        val mapFactory = MapFromFileGenerator("src/test/resources/test-map.txt")
        val worldFactory = WorldFactory(mapFactory)
        var world = worldFactory.createWorld()
        val simulator = SimulatorImpl()
        world = simulator.simulate(world) {
            when (it) {
                is PlayerUnit -> {
                    MoveAction.LEFT
                }
                else -> {
                    Procrastinate
                }
            }
        }
        Assertions.assertTrue(world.player.position == Position(1, 2))
    }
}
