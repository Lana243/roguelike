package roguelike.state.game.simulator

import roguelike.state.game.world.Cell
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit

/**
 * Реализация интерфейса [Simulator]
 */
class SimulatorImpl : Simulator {

    override fun simulate(world: World, actions: (GameUnit) -> UnitAction): World {
        val player = world.player
        val playerAction = actions(player)
        return process(world, player, playerAction) ?: world
    }

    private fun process(world: World, player: PlayerUnit, action: UnitAction): World? =
        when (action) {
            is MoveAction -> processMove(world, player, action)
            is Equip -> processEquip(world, player, action)
            is Unequip -> processUnequip(world, player, action)
            Interact -> processInteract(world, player)
            Procrastinate -> world
        }

    private fun processUnequip(world: World, player: PlayerUnit, action: Unequip): World? =
        TODO()

    private fun processEquip(world: World, player: PlayerUnit, action: Equip): World? =
        TODO()

    private fun processMove(world: World, player: PlayerUnit, action: MoveAction): World {
        val newPosition = world.player.position + action
        val toCell = world.map.getCell(newPosition)

        if (toCell !is Cell.Solid) {
            world.map.moveCell(world.player.position, newPosition)
            world.player.position = newPosition
        }

        if (toCell is Cell.StaticObject) {
            if (toCell.staticObject is ExitDoor) {
                world.map.setCell(player.position, Cell.Empty) // victory
            }
            // TODO: Well, ...
        }

        if (toCell is Cell.Item) {
            if (toCell.item is Apple) {
                val apple = toCell.item
                player.updateHp(+ apple.healsHp)
            }
            // TODO: Sword, ...
        }

        return world
    }

    private fun processInteract(world: World, player: PlayerUnit): World? =
        TODO()
}