package roguelike.state.game.simulator

import roguelike.state.game.world.Cell
import roguelike.state.game.world.Position
import roguelike.state.game.world.World
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.Effect
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.Sword
import roguelike.state.game.world.objects.Well
import roguelike.state.game.world.objects.units.ContusionStrategy
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.Mob
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.game.world.objects.units.foundItem
import roguelike.state.game.world.objects.units.toggle

/**
 * Реализация интерфейса [Simulator]
 */
class SimulatorImpl : Simulator {

    override fun simulate(world: World, actions: Map<GameUnit, (World) -> UnitAction>): World {
        var currentWorld = world

        processEffects(currentWorld)

        for ((unit, action) in actions) {
            if (unit.hp > 0) {
                currentWorld = process(world, unit, action(currentWorld))
            }
        }
        currentWorld.tick++

        return currentWorld
    }

    private fun processEffects(world: World) {
        val it = world.effects.iterator()
        while (it.hasNext()) {
            val remove = it.next().update(world.tick)
            if (remove) {
                it.remove()
            }
        }
    }

    private fun process(world: World, unit: GameUnit, action: UnitAction): World =
        when (action) {
            is MoveAction -> processMove(world, unit, action)
            is ToggleInventoryItem -> processToggleInventoryItem(world, unit, action)
            Interact -> processInteract(world, unit)
            Procrastinate -> world
        }

    private fun processToggleInventoryItem(world: World, unit: GameUnit, action: ToggleInventoryItem): World {
        if (unit !is PlayerUnit) {
            return world
        }
        unit.inventory.toggle(action.pos)
        return world
    }

    private fun processMove(world: World, unit: GameUnit, action: MoveAction): World {
        val newPosition = unit.position + action
        val toCell = world.map.getCell(newPosition)

        if (unit is PlayerUnit) {
            processCellMoveForPlayer(world, unit, newPosition)
        }

        if (toCell is Cell.Unit) {
            return processFight(world, unit, toCell.unit)
        }

        if (toCell !is Cell.Solid) {
            world.map.moveCell(unit.position, newPosition)
            unit.position = newPosition
        }


        return world
    }

    private fun processFight(world: World, attacker: GameUnit, defender: GameUnit): World {
        defender.updateHp(-attacker.attackRate)

        if (defender.hp <= 0) {
            world.units.remove(defender.id)
            world.map.setCell(defender.position, Cell.Empty)

            world.map.setCell(attacker.position, Cell.Empty)
            attacker.position = defender.position
            world.map.setCell(defender.position, Cell.Unit(attacker))

            if (attacker is PlayerUnit) {
                attacker.updateExp()
            }
        }

        if (attacker is PlayerUnit && attacker.isSwordEquipped() && defender is Mob) {
            val contusionStrategy = ContusionStrategy(defender.strategy)
            defender.strategy = contusionStrategy
            val startTick = world.tick

            val contusionEffect = Effect { tick ->
                if (tick == startTick + 5) {
                    defender.strategy = contusionStrategy.baseStrategy
                    true
                } else {
                    false
                }
            }

            world.effects.add(contusionEffect)
        }

        return world
    }


    private fun processCellMoveForPlayer(world: World, player: PlayerUnit, position: Position): World {
        val toCell = world.map.getCell(position)
        if (toCell is Cell.StaticObject) {
            if (toCell.staticObject is ExitDoor) {
                world.map.moveCell(player.position, position)
                world.victory = true
            }
        }

        if (toCell is Cell.Item) {
            if (toCell.item is Apple) {
                val apple = toCell.item
                player.updateHp(+apple.healsHp)
            }
            if (toCell.item is Sword) {
                val sword = toCell.item
                player.inventory.foundItem(sword)
            }
        }

        return world
    }

    private fun processInteract(world: World, player: GameUnit): World {
        if (player is PlayerUnit) {
            return processInteractForPlayer(world, player)
        }
        return world
    }

    private fun processInteractForPlayer(world: World, player: PlayerUnit): World {
        for (moveAction in MoveAction.values()) {
            val newPosition = world.player.position + moveAction
            val toCell = world.map.getCell(newPosition)

            if (toCell is Cell.StaticObject && toCell.staticObject is Well) {
                val well = toCell.staticObject
                if (!well.visited) {
                    player.levelUp()
                    well.visited = true
                }
            }
        }
        return world
    }
}