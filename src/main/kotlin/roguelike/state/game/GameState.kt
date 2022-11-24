package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.defeat.DefeatScreenState
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.Simulator
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.world.MapFactory
import roguelike.state.game.world.MapLevel1
import roguelike.state.game.world.MapRandomGenerator
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.objects.units.Mob
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.menu.MenuMessage
import roguelike.state.menu.MenuScreenState
import roguelike.state.victory.VictoryScreenState


/**
 * состояние игры
 */
class GameState(
    private val initialWorld: World? = null,
    private val createdByMessage: Message? = null,
) : State() {

    val world: World by lazy {
        initialWorld ?: worldFactory.createWorld()
    }

    override fun process(message: Message): State {
        return when (message) {
            is GameMessage -> {
                when (message) {
                    is GameMessage.PlayerActionMessage -> {
                        val actions = world.units.map { (_, unit) ->
                            when (unit) {
                                is PlayerUnit -> {
                                    unit to { _: World -> message.action }
                                }
                                is Mob -> {
                                    unit to { world: World -> unit.strategy.getNextAction(unit, world) }
                                }
                                else -> {
                                    unit to { _: World -> Procrastinate }
                                }
                            }
                        }.toMap().toSortedMap(Comparator.comparingInt { it.id })

                        val newWorld = simulator.simulate(world, actions)

                        when {
                            newWorld.victory -> {
                                VictoryScreenState()
                            }
                            newWorld.defeat -> {
                                DefeatScreenState()
                            }
                            else -> {
                                GameState(newWorld)
                            }
                        }
                    }
                    GameMessage.Exit -> MenuScreenState()
                }
            }
            else -> error("Unknown message")
        }
    }

    // internal

    private val mapFactory: MapFactory = run {
        when (createdByMessage) {
            MenuMessage.StartGameLevel1 -> MapLevel1()
            MenuMessage.StartGameQuick -> MapRandomGenerator(80, 23)
            else -> MapRandomGenerator(80, 23)
        }
    }

    private val worldFactory = WorldFactory(mapFactory)

    private val simulator: Simulator = SimulatorImpl()
}
