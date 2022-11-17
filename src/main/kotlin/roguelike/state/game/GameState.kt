package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.simulator.Simulator
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.simulator.UnitAction
import roguelike.state.game.world.MapFactory
import roguelike.state.game.world.MapGenerator
import roguelike.state.game.world.World
import roguelike.state.game.world.WorldFactory
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.menu.MenuScreenState

class GameState(
    private val initialWorld: World? = null
) : State() {

    val world: World by lazy {
        initialWorld ?: worldFactory.createWorld()
    }

    override fun process(message: Message): State {
        return when (message) {
            is GameMessage -> {
                when (message) {
                    is GameMessage.PlayerActionMessage -> {
                        val actions = { gameUnit: GameUnit ->
                            when (gameUnit) {
                                is PlayerUnit -> message.action
                                else -> TODO("Not implemented")
                            }
                        }
                        val newWorld = simulator.simulate(world, actions)
                        GameState(newWorld)
                    }
                    GameMessage.Exit -> MenuScreenState()
                }
            }
            else -> error("Unknown message")
        }
    }

    // internal

    private val mapFactory: MapFactory = MapGenerator()

    private val worldFactory = WorldFactory(mapFactory)

    private val simulator: Simulator = SimulatorImpl()
}
