package roguelike

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.Interact
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.simulator.ToggleInventoryItem
import roguelike.state.game.world.Position
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.map.MapBuilder
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.Sword

class PlayerTest {

    private lateinit var world: World
    private lateinit var simulator: SimulatorImpl

    @BeforeEach
    fun initWorld() {
        val mapBuilder = MapBuilder().fromFile("src/test/resources/test-map.txt")
        val worldFactory = WorldFactory(mapBuilder)
        world = worldFactory.createWorld()
        simulator = SimulatorImpl()
    }

    @Test
    fun `If player moves to the wall, his position does not change`() {
        simulator.simulate(world, mapOf(world.player to { MoveAction(-1, 0) }))
        Assertions.assertEquals(Position(1, 2), world.player.position)
    }

    @Test
    fun `If player goes to cell with apple, his health increases`() {
        val initialHp = 5
        Assertions.assertEquals(initialHp, world.player.hp)
        repeat(3) {
            simulator.simulate(world, mapOf(world.player to { MoveAction(0, 1) }))
        }
        val expectedHp = initialHp + Apple(228).healsHp
        Assertions.assertEquals(expectedHp, world.player.hp)
    }

    @Test
    fun `If player goes to well and interacts, his level increments`() {
        Assertions.assertEquals(1, world.player.level)
        repeat(9) {
            simulator.simulate(world, mapOf(world.player to { MoveAction(1, 0) }))
        }
        simulator.simulate(world, mapOf(world.player to { Interact }))
        Assertions.assertEquals(2, world.player.level)
    }

    @Test
    fun `If player goes to the cell with well, his position does not change`() {
        repeat(9) {
            simulator.simulate(world, mapOf(world.player to { MoveAction(1, 0) }))
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
        simulator.simulate(world, mapOf(world.player to { MoveAction(1, 0) }))
        Assertions.assertEquals(Position(11, 2), world.player.position)
    }

    @Test
    fun `Player cannot interact with the well twice`() {
        Assertions.assertEquals(1, world.player.level)
        repeat(9) {
            simulator.simulate(world, mapOf(world.player to { MoveAction(1, 0) }))
        }
        simulator.simulate(world, mapOf(world.player to { Interact }))
        Assertions.assertEquals(2, world.player.level)
        simulator.simulate(world, mapOf(world.player to { Interact }))
        Assertions.assertEquals(2, world.player.level)
    }

    @Test
    fun `If player picks a sword up, the sword moves to his inventory`() {
        repeat(9) {
            simulator.simulate(world, mapOf(world.player to { MoveAction(1, 0) }))
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
        simulator.simulate(world, mapOf(world.player to { MoveAction(0, 1) }))
        Assertions.assertTrue(world.player.inventory.items.first().item is Sword)
    }

    @Test
    fun `If player toggles a sword, his attack rate increases`() {
        repeat(9) {
            simulator.simulate(world, mapOf(world.player to { MoveAction(1, 0) }))
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
        simulator.simulate(world, mapOf(world.player to { MoveAction(0, 1) }))
        simulator.simulate(world, mapOf(world.player to { ToggleInventoryItem(0) }))
        Assertions.assertEquals(world.player.baseAttackRate + Sword(-1).extraDamage, world.player.attackRate)
    }

    @Test
    fun `If player moves to the door, he wins`() {
        repeat(18) {
            simulator.simulate(world, mapOf(
                world.player to { MoveAction(0, 1) }
            ))
        }
        repeat(75) {
            simulator.simulate(world, mapOf(
                world.player to { MoveAction(1, 0) }
            ))
        }

        Assertions.assertTrue(world.victory)
    }
}
