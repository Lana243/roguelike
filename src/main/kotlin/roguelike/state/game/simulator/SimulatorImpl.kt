package roguelike.state.game.simulator

import roguelike.state.game.world.Cell
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.Sword
import roguelike.state.game.world.objects.Well
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.game.world.objects.units.foundItem
import roguelike.state.game.world.objects.units.toggle

/**
 * Реализация интерфейса [Simulator]
 */
class SimulatorImpl : Simulator {

    override fun simulate(world: World, actions: (GameUnit) -> UnitAction): World {
        val player = world.player
        val playerAction = actions(player)
        return process(world, player, playerAction) ?: world
    }

    private fun process(world: World, player: PlayerUnit, action: UnitAction): World =
        when (action) {
            is MoveAction -> processMove(world, player, action)
            is ToggleInventoryItem -> processToggleInventoryItem(world, player, action)
            Interact -> processInteract(world, player)
            Procrastinate -> world
        }

    private fun processToggleInventoryItem(world: World, player: PlayerUnit, action: ToggleInventoryItem): World {
        player.inventory.toggle(action.pos)
        return world
    }

    private fun processMove(world: World, player: PlayerUnit, action: MoveAction): World {
        val newPosition = world.player.position + action
        val toCell = world.map.getCell(newPosition)

        if (toCell !is Cell.Solid) {
            world.map.moveCell(world.player.position, newPosition)
            world.player.position = newPosition
        }

        if (toCell is Cell.StaticObject) {
            if (toCell.staticObject is ExitDoor) {
                world.map.setCell(player.position, Cell.Empty)
                world.victory = true
            }
        }

        if (toCell is Cell.Item) {
            if (toCell.item is Apple) {
                val apple = toCell.item
                player.updateHp(+ apple.healsHp)
            }
            if (toCell.item is Sword) {
                val sword = toCell.item
                player.inventory.foundItem(sword)
            }
        }

        return world
    }

    private fun processInteract(world: World, player: PlayerUnit): World {
        for (moveAction in MoveAction.values()) {
            val newPosition = world.player.position + moveAction
            val toCell = world.map.getCell(newPosition)

            if (toCell is Cell.StaticObject && toCell.staticObject is Well) {
                val well = toCell.staticObject
                if (!well.visited) {
                    player.level++
                    well.visited = true
                }
            }
        }
        return world
    }
}