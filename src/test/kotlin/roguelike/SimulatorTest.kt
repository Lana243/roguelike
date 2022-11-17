package roguelike

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.Interact
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.world.MapFromFileGenerator
import roguelike.state.game.world.Position
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.objects.units.PlayerUnit

class SimulatorTest {

    lateinit var world: World
    lateinit var simulator: SimulatorImpl

    @BeforeEach
    fun initWorld() {
        val mapFactory = MapFromFileGenerator("src/test/resources/test-map.txt")
        val worldFactory = WorldFactory(mapFactory)
        world = worldFactory.createWorld()
        simulator = SimulatorImpl()
    }

    @Test
    fun `If player moves to the wall, his position does not change`() {
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
        Assertions.assertEquals(Position(1, 2), world.player.position)
    }

    @Test
    fun `If player goes to cell with apple, his health increases`() {
        Assertions.assertEquals(50, world.player.hp)
        repeat(3) {
            world = simulator.simulate(world) {
                when (it) {
                    is PlayerUnit -> {
                        MoveAction.DOWN
                    }
                    else -> {
                        Procrastinate
                    }
                }
            }
        }
        Assertions.assertEquals(60, world.player.hp)
    }

    @Disabled
    @Test
    fun `If player goes to well and interacts, his level increments`() {
        Assertions.assertEquals(1, world.player.level)
        repeat(9) {
            world = simulator.simulate(world) {
                when (it) {
                    is PlayerUnit -> {
                        MoveAction.RIGHT
                    }
                    else -> {
                        Procrastinate
                    }
                }
            }
        }
        world = simulator.simulate(world) {
            when (it) {
                is PlayerUnit -> {
                    Interact
                }
                else -> {
                    Procrastinate
                }
            }
        }
        Assertions.assertEquals(2, world.player.level)
    }

    @Test
    fun `If player goes to the cell with well, his position does not change`() {
        repeat(9) {
            world = simulator.simulate(world) {
                when (it) {
                    is PlayerUnit -> {
                        MoveAction.RIGHT
                    }
                    else -> {
                        Procrastinate
                    }
                }
            }
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
        world = simulator.simulate(world) {
            when (it) {
                is PlayerUnit -> {
                    MoveAction.RIGHT
                }
                else -> {
                    Procrastinate
                }
            }
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
    }
}
