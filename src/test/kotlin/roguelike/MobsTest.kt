package roguelike

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.simulator.ToggleInventoryItem
import roguelike.state.game.world.map.MapFromFileGenerator
import roguelike.state.game.world.Position
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.objects.units.ContusionStrategy
import roguelike.state.game.world.objects.units.Mob

class MobsTest {

    private lateinit var world: World
    private lateinit var simulator: SimulatorImpl

    @BeforeEach
    fun initWorld() {
        val mapFactory = MapFromFileGenerator("src/test/resources/test-map.txt")
        val worldFactory = WorldFactory(mapFactory)
        world = worldFactory.createWorld()
        simulator = SimulatorImpl()
    }

    @Test
    fun `If player and mob move to each other, their health decreases`() {
        val initialPlayerHp = world.player.hp
        val playerAttack = world.player.attackRate
        val initialMobHp = world.units[4]!!.hp
        val mobAttack = world.units[4]!!.attackRate
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[4]!! to { MoveAction.UP }
        ))
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[4]!! to { MoveAction.LEFT }
        ))

        Assertions.assertEquals(Position(3, 2), world.player.position)
        Assertions.assertEquals(Position(4, 2), world.units[4]!!.position)
        Assertions.assertEquals(world.player.hp, initialPlayerHp - mobAttack)
        Assertions.assertEquals(world.units[4]!!.hp, initialMobHp - playerAttack)
    }

    @Test
    fun `If player moves to mob, mob's health decreases`() {
        val playerAttack = world.player.attackRate
        val initialMobHp = world.units[4]!!.hp
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[4]!! to { MoveAction.UP }
        ))
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT }
        ))

        Assertions.assertEquals(Position(3, 2), world.player.position)
        Assertions.assertEquals(world.units[4]!!.hp, initialMobHp - playerAttack)
    }

    @Test
    fun `If mob moves to player, player health decreases`() {
        val initialPlayerHp = world.player.hp
        val mobAttack = world.units[4]!!.attackRate
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[4]!! to { MoveAction.UP }
        ))
        world = simulator.simulate(world, mapOf(
            world.units[4]!! to { MoveAction.LEFT }
        ))

        Assertions.assertEquals(Position(4, 2), world.units[4]!!.position)
        Assertions.assertEquals(world.player.hp, initialPlayerHp - mobAttack)
    }

    @Test
    fun `If player kills mob, player experience grows and his position changes`() {
        Assertions.assertEquals(0, world.player.exp)
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[4]!! to { MoveAction.UP }
        ))

        while (world.units.containsKey(4)) {
            world = simulator.simulate(world, mapOf(
                world.player to { MoveAction.RIGHT }
            ))
        }

        Assertions.assertEquals(1, world.player.exp)
        Assertions.assertEquals(Position(4, 2), world.player.position)
    }

    @Test
    fun `If player moves to the mob with sword, mob's strategy changes`() {
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.UP },
            world.units[4]!! to { MoveAction.UP }
        ))
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.DOWN },
            world.units[4]!! to { MoveAction.LEFT }
        ))
        world = simulator.simulate(world, mapOf(
            world.player to { ToggleInventoryItem(0) },
            world.units[4]!! to { Procrastinate }
        ))
        world = simulator.simulate(world, mapOf(
            world.player to { MoveAction.RIGHT },
            world.units[4]!! to { Procrastinate }
        ))

        Assertions.assertEquals(Position(2, 2), world.player.position)
        Assertions.assertTrue(world.units[4]!!.run { this is Mob && this.strategy is ContusionStrategy })
        Assertions.assertTrue(world.effects.size == 1)
    }

    @Test
    fun `If mob kills player, player loses`() {
        repeat(2) {
            world = simulator.simulate(world, mapOf(
                world.units[4]!! to { MoveAction.LEFT }
            ))
        }
        val stepsToKill =
            world.player.hp / world.units[4]!!.attackRate + if (world.player.hp % world.units[4]!!.attackRate == 0) 0 else 1
        repeat(stepsToKill) {
            world = simulator.simulate(world, mapOf(
                world.units[4]!! to { MoveAction.UP }
            ))
        }
        Assertions.assertTrue(world.defeat)
    }

    @Test
    fun `Mobs cannot go to door cell`() {
        repeat(17) {
            world = simulator.simulate(world, mapOf(
                world.units[4]!! to { MoveAction.DOWN }
            ))
        }
        repeat(73) {
            world = simulator.simulate(world, mapOf(
                world.units[4]!! to { MoveAction.RIGHT }
            ))
        }
        Assertions.assertEquals(Position(76, 20), world.units[4]!!.position)
    }

    @Test
    fun `Mob cannot go to cell with wall`() {
        world = simulator.simulate(world, mapOf(
            world.units[4]!! to { MoveAction.RIGHT }
        ))
        Assertions.assertEquals(Position(4, 3), world.units[4]!!.position)
    }

    @Test
    fun `Mob can attack another mob`() {
        val initialSndMobHp = world.units[8]!!.hp
        repeat(4) {
            world = simulator.simulate(world, mapOf(
                world.units[4]!! to { MoveAction.DOWN }
            ))
        }
        repeat(4) {
            world = simulator.simulate(world, mapOf(
                world.units[4]!! to { MoveAction.RIGHT }
            ))
        }
        Assertions.assertEquals(initialSndMobHp - world.units[4]!!.attackRate, world.units[8]!!.hp)
    }
}
