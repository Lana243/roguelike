package roguelike.state.game

import roguelike.state.Message
import roguelike.state.State
import roguelike.state.game.simulator.Simulator
import roguelike.state.game.simulator.SimulatorImpl
import roguelike.state.game.world.*
import roguelike.state.game.world.objects.units.GameUnit
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
                        val actions = { gameUnit: GameUnit ->
                            when (gameUnit) {
                                is PlayerUnit -> message.action
                                else -> TODO("Not implemented")
                            }
                        }
                        val newWorld = simulator.simulate(world, actions)

                        if (newWorld.victory) {
                            VictoryScreenState()
                        } else {
                            GameState(newWorld)
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
