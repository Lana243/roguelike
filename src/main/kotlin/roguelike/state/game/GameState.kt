package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.defeat.DefeatScreenState
import roguelike.state.game.simulator.Procrastinate
import roguelike.state.game.simulator.Simulator
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.map.MapBuilder
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.game.world.objects.units.mob.Mob
import roguelike.state.menu.MenuMessage
import roguelike.state.menu.MenuScreenState
import roguelike.state.victory.VictoryScreenState

/**
 * Состояние игры.
 */
data class GameState(
    val world: World,
) : State() {
    companion object {
        fun create(byMessage: Message? = null): GameState {
            var mapBuilder = MapBuilder()
            when (byMessage) {
                MenuMessage.StartGameLevel1 -> {
                    mapBuilder = mapBuilder.fromFile(LEVEL_1_MAP_PATH)
                }
                MenuMessage.StartGameLevel2 -> {
                    mapBuilder = mapBuilder.fromFile(LEVEL_2_MAP_PATH)
                }
                MenuMessage.StartGameQuick -> {
                    mapBuilder = mapBuilder
                        .setSize(SCREEN_LENGTH_X, SCREEN_LENGTH_Y - 1)
                        .randomGenerate()
                }
                else -> {}
            }
            val worldFactory = WorldFactory(mapBuilder)
            val world = worldFactory.createWorld()
            return GameState(world)
        }
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
                                    unit to { world: World -> unit.state.strategy.getNextAction(unit, world) }
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
                                this.copy(world = newWorld)
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

    private val simulator: Simulator = SimulatorImpl()
}
