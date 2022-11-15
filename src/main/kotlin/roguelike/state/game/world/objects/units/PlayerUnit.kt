package roguelike.state.game.world.objects.units

import roguelike.state.game.GameMessage
import roguelike.state.game.simulator.Interact
import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.world.Position


class PlayerUnit(
    override var position: Position = Position(0, 0),
    override var attackRate: UInt = 1U,
    override var hp: UInt = 100U,
    override val maxHp: UInt = 100U,
    var exp: UInt = 0U,
    var level: UInt = 1U,
    var inventory: Inventory = Inventory(emptyList()),
) : GameUnit() {
    fun process(message: GameMessage.PlayerActionMessage) {
        when (message.action) {
            is MoveAction -> {
                position += message.action
            }
            Interact -> TODO()
        }
    }
}

data class Inventory(
    val itemIds: List<Int>
)