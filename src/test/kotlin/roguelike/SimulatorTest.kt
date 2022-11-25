package roguelike

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.Interact
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.simulator.ToggleInventoryItem
import roguelike.state.game.world.MapFromFileGenerator
import roguelike.state.game.world.Position
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.Sword

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
        world = simulator.simulate(world, mapOf(world.player to { MoveAction.LEFT }))
        Assertions.assertEquals(Position(1, 2), world.player.position)
    }

    @Test
    fun `If player goes to cell with apple, his health increases`() {
        val initialHp = 5
        val expectedHp = initialHp + Apple(228).healsHp
        Assertions.assertEquals(initialHp, world.player.hp)
        repeat(3) {
            world = simulator.simulate(world, mapOf(world.player to { MoveAction.DOWN }))
        }
        Assertions.assertEquals(expectedHp, world.player.hp)
    }

    @Test
    fun `If player goes to well and interacts, his level increments`() {
        Assertions.assertEquals(1, world.player.level)
        repeat(9) {
            world = simulator.simulate(world, mapOf(world.player to { MoveAction.RIGHT }))
        }
        world = simulator.simulate(world, mapOf(world.player to { Interact }))
        Assertions.assertEquals(2, world.player.level)
    }

    @Test
    fun `If player goes to the cell with well, his position does not change`() {
        repeat(9) {
            world = simulator.simulate(world, mapOf(world.player to { MoveAction.RIGHT }))
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
        world = simulator.simulate(world, mapOf(world.player to { MoveAction.RIGHT }))
        Assertions.assertEquals(Position(11, 2), world.player.position)
    }

    @Test
    fun `If player picks a sword up, the sword moves to his inventory`() {
        repeat(9) {
            world = simulator.simulate(world, mapOf(world.player to { MoveAction.RIGHT }))
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
        world = simulator.simulate(world, mapOf(world.player to { MoveAction.DOWN }))
        Assertions.assertTrue(world.player.inventory.items.first().item is Sword)
    }


    @Test
    fun `If player toggles a sword, his attack rate increases`() {
        repeat(9) {
            world = simulator.simulate(world, mapOf(world.player to { MoveAction.RIGHT }))
        }
        Assertions.assertEquals(Position(11, 2), world.player.position)
        world = simulator.simulate(world, mapOf(world.player to { MoveAction.DOWN }))
        world = simulator.simulate(world, mapOf(world.player to { ToggleInventoryItem(0) }))
        Assertions.assertEquals(world.player.baseAttackRate + Sword(-1).extraDamage, world.player.attackRate)
    }

    @Test
    fun `If player and mob move to each other, their health decreases`() {
        val initialPlayerHp = world.player.hp
        val playerAttack = world.player.attackRate
        val initialMobHp = world.units[3]!!.hp
        val mobAttack = world.units[3]!!.attackRate
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[3]!! to { MoveAction.UP }
        ))
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[3]!! to { MoveAction.LEFT }
        ))

        Assertions.assertEquals(Position(3, 2), world.player.position)
        Assertions.assertEquals(Position(4, 2), world.units[3]!!.position)
        Assertions.assertEquals(world.player.hp, initialPlayerHp - mobAttack)
        Assertions.assertEquals(world.units[3]!!.hp, initialMobHp - playerAttack)
    }

    @Test
    fun `If player moves to mob, mob's health decreases`() {
        val playerAttack = world.player.attackRate
        val initialMobHp = world.units[3]!!.hp
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[3]!! to { MoveAction.UP }
        ))
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT }
        ))

        Assertions.assertEquals(Position(3, 2), world.player.position)
        Assertions.assertEquals(world.units[3]!!.hp, initialMobHp - playerAttack)
    }

    @Test
    fun `If mob moves to player, player health decreases`() {
        val initialPlayerHp = world.player.hp
        val mobAttack = world.units[3]!!.attackRate
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[3]!! to { MoveAction.UP }
        ))
        world = simulator.simulate(world, mapOf(
            world.units[3]!! to { MoveAction.LEFT }
        ))

        Assertions.assertEquals(Position(4, 2), world.units[3]!!.position)
        Assertions.assertEquals(world.player.hp, initialPlayerHp - mobAttack)
    }

    @Test
    fun `If player kills mob, player experience grows and his position changes`() {
        Assertions.assertEquals(0, world.player.exp)
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[3]!! to { MoveAction.UP }
        ))

        while (world.units.containsKey(3)) {
            world = simulator.simulate(world, mapOf(
                world.player to {MoveAction.RIGHT}
            ))
        }

        Assertions.assertEquals(1, world.player.exp)
        Assertions.assertEquals(Position(4, 2), world.player.position)
    }
}
