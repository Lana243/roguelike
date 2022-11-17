package roguelike.state.game.simulator

import roguelike.state.game.world.Cell
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit

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
        }

    private fun processUnequip(world: World, player: PlayerUnit, action: Unequip): World? =
        TODO()

    private fun processEquip(world: World, player: PlayerUnit, action: Equip): World? =
        TODO()

    private fun processMove(world: World, player: PlayerUnit, action: MoveAction): World {
        val newPosition = world.player.position + action
        if (world.map.getCell(newPosition) !is Cell.Solid) {
            world.map.moveCell(world.player.position, newPosition)
            world.player.position = newPosition
        }
        return world
    }

    private fun processInteract(world: World, player: PlayerUnit): World? =
        TODO()
}