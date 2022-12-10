package roguelike

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.Simulator
import roguelike.state.game.simulator.ToggleInventoryItem
import roguelike.state.game.world.Position
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.map.MapBuilder
import roguelike.state.game.world.objects.units.mob.strategies.ContusionStrategy
import roguelike.state.game.world.objects.units.mob.strategies.PassiveStrategy
import roguelike.state.game.world.objects.units.mob.strategies.HpBasedStrategy
import roguelike.state.game.world.objects.units.mob.Mob
import roguelike.state.game.world.objects.units.mob.passiveMobFactory

class MobsTest {

    private lateinit var world: World
    private lateinit var simulator: Simulator
    private lateinit var mobs: List<Mob>

    @BeforeEach
    fun initWorld() {
        val mapBuilder = MapBuilder().fromFile("src/test/resources/test-map.txt")
        val worldFactory = WorldFactory(mapBuilder, passiveMobFactory())
        world = worldFactory.createWorld()
        simulator = Simulator()
        mobs = listOf(world.units[4]!! as Mob, world.units[8]!! as Mob)
        mobs[0].strategy = HpBasedStrategy(PassiveStrategy(), PassiveStrategy())
    }

    @Test
    fun `If player and mob move to each other, their health decreases`() {
        val initialPlayerHp = world.player.hp
        val playerAttack = world.player.attackRate
        val mob = mobs[0]
        val initialMobHp = mob.hp
        val mobAttack = mob.attackRate
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(1, 0) },
            mob to { MoveAction(0, -1) }
        ))
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(1, 0) },
            mob to { MoveAction(-1, 0) }
        ))

        Assertions.assertEquals(Position(3, 2), world.player.position)
        Assertions.assertEquals(Position(4, 2), mob.position)
        Assertions.assertEquals(world.player.hp, initialPlayerHp - mobAttack)
        Assertions.assertEquals(mob.hp, initialMobHp - playerAttack)
    }

    @Test
    fun `If player moves to mob, mob's health decreases`() {
        val playerAttack = world.player.attackRate
        val mob = mobs[0]
        val initialMobHp = mob.hp
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(1, 0) },
            mob to { MoveAction(0, -1) }
        ))
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(1, 0) }
        ))

        Assertions.assertEquals(Position(3, 2), world.player.position)
        Assertions.assertEquals(mob.hp, initialMobHp - playerAttack)
    }

    @Test
    fun `If mob moves to player, player health decreases`() {
        val initialPlayerHp = world.player.hp
        val mob = mobs[0]
        val mobAttack = mob.attackRate
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(1, 0) },
            mob to { MoveAction(0, -1) }
        ))
        simulator.simulate(world, mapOf(
            mob to { MoveAction(-1, 0) }
        ))

        Assertions.assertEquals(Position(4, 2), mob.position)
        Assertions.assertEquals(world.player.hp, initialPlayerHp - mobAttack)
    }

    @Test
    fun `If player kills mob, player experience grows and his position changes`() {
        Assertions.assertEquals(0, world.player.exp)
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(1, 0) },
            mobs[0] to { MoveAction(0, -1) }
        ))

        while (world.units.containsKey(4)) {
            simulator.simulate(world, mapOf(
                world.player to { MoveAction(1, 0) }
            ))
        }

        Assertions.assertEquals(1, world.player.exp)
        Assertions.assertEquals(Position(4, 2), world.player.position)
    }

    @Test
    fun `If player moves to the mob with sword, mob's strategy changes`() {
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(0, -1) },
            mobs[0] to { MoveAction(0, -1) }
        ))
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(0, 1) },
            mobs[0] to { MoveAction(-1, 0) }
        ))
        simulator.simulate(world, mapOf(
            world.player to { ToggleInventoryItem(0) },
            mobs[0] to { Procrastinate }
        ))
        simulator.simulate(world, mapOf(
            world.player to { MoveAction(1, 0) },
            mobs[0] to { Procrastinate }
        ))

        Assertions.assertEquals(Position(2, 2), world.player.position)
        Assertions.assertTrue(mobs[0].run { this.strategy is ContusionStrategy })
        Assertions.assertTrue(world.effects.size == 1)
    }

    @Test
    fun `If mob kills player, player loses`() {
        repeat(2) {
            simulator.simulate(world, mapOf(
                mobs[0] to { MoveAction(-1, 0) }
            ))
        }
        val stepsToKill = 3
        repeat(stepsToKill) {
            simulator.simulate(world, mapOf(
                mobs[0] to { MoveAction(0, -1) }
            ))
        }
        Assertions.assertTrue(world.defeat)
    }

    @Test
    fun `Mobs cannot go to door cell`() {
        repeat(17) {
            simulator.simulate(world, mapOf(
                mobs[0] to { MoveAction(0, 1) }
            ))
        }
        repeat(73) {
            simulator.simulate(world, mapOf(
                mobs[0] to { MoveAction(1, 0) }
            ))
        }
        Assertions.assertEquals(Position(76, 20), mobs[0].position)
    }

    @Test
    fun `Mob cannot go to cell with wall`() {
        simulator.simulate(world, mapOf(
            mobs[0] to { MoveAction(1, 0) }
        ))
        Assertions.assertEquals(Position(4, 3), mobs[0].position)
    }

    @Test
    fun `Mob can attack another mob`() {
        val initialSndMobHp = mobs[1].hp
        repeat(4) {
            simulator.simulate(world, mapOf(
                mobs[0] to { MoveAction(0, 1) }
            ))
        }
        repeat(4) {
            simulator.simulate(world, mapOf(
                mobs[0] to { MoveAction(1, 0) }
            ))
        }
        Assertions.assertEquals(initialSndMobHp - mobs[0].attackRate, mobs[1].hp)
    }

    @Test
    fun `Strategy's initial state is GoodHealthState`() {
        val mob = mobs[0]
        Assertions.assertTrue((mob.strategy as HpBasedStrategy).isInGoodState())
    }

    @Test
    fun `Strategy changes state if hp leq than maxHp`() {
        val mob = mobs[0]
        mob.updateHp(-2)
        mob.strategy.getNextAction(mob, world)
        Assertions.assertFalse((mob.strategy as HpBasedStrategy).isInGoodState())
    }

    @Test
    fun `Strategy does not change state after updateHp if hp greater than maxHp`() {
        val mob = mobs[0]
        mob.updateHp(+1)
        mob.strategy.getNextAction(mob, world)
        Assertions.assertTrue((mob.strategy as HpBasedStrategy).isInGoodState())
    }

    @Test
    fun `Strategy does not change state back after increasing hp`() {
        val mob = mobs[0]
        mob.updateHp(+1)
        mob.strategy.getNextAction(mob, world)
        Assertions.assertTrue((mob.strategy as HpBasedStrategy).isInGoodState())
    }
}
