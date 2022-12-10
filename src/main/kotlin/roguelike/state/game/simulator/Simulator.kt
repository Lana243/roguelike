package roguelike.state.game.simulator

import roguelike.state.game.world.Position
import roguelike.state.game.world.World
import roguelike.state.game.world.getCorrectMoves
import roguelike.state.game.world.map.Cell
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.Effect
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.Sword
import roguelike.state.game.world.objects.Well
import roguelike.state.game.world.objects.units.Clone
import roguelike.state.game.world.objects.units.ContusionStrategy
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.game.world.objects.units.foundItem
import roguelike.state.game.world.objects.units.mob.CloneableMob
import roguelike.state.game.world.objects.units.mob.GoodHealthState
import roguelike.state.game.world.objects.units.mob.Mob
import roguelike.state.game.world.objects.units.toggle

/**
 * Реализация интерфейса [Simulator]
 */
class Simulator {
    private val fightProcessor = FightProcessor()
    private val interactionProcessor = InteractionProcessor()
    private val mobActionProcessor = MobActionProcessor()

    fun simulate(world: World, actions: Map<GameUnit, (World) -> UnitAction>) {
        processEffects(world)

        for ((unit, action) in actions) {
            if (unit.hp > 0) {
                process(world, unit, action(world))
            }
        }

        world.tick++
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

    private fun process(world: World, unit: GameUnit, action: UnitAction) =
        when (action) {
            is MoveAction -> processMove(world, unit, action)
            is ToggleInventoryItem -> processToggleInventoryItem(world, unit, action)
            Interact -> interactionProcessor.processInteract(world, unit)
            Procrastinate -> {}
            is MobAction -> mobActionProcessor.processMobAction(world, unit, action)
        }


    private fun processToggleInventoryItem(world: World, unit: GameUnit, action: ToggleInventoryItem) {
        if (unit !is PlayerUnit) {
            return
        }
        unit.inventory.toggle(action.pos)
    }

    private fun processMove(world: World, unit: GameUnit, action: MoveAction) {
        val newPosition = unit.position + action
        val toCell = world.map.getCell(newPosition)

        if (unit is PlayerUnit) {
            processCellMoveForPlayer(world, unit, newPosition)
        }

        if (toCell is Cell.Unit) {
            fightProcessor.processFight(world, unit, toCell.unit)
            return
        }

        if (toCell is Cell.Item && toCell.item is Apple) {
            val apple = toCell.item
            unit.updateHp(+apple.healsHp)
            world.items.remove(newPosition)
        }

        if (toCell !is Cell.Solid) {
            world.map.moveCell(unit.position, newPosition)
            unit.position = newPosition
        }
    }

    private fun processCellMoveForPlayer(world: World, player: PlayerUnit, position: Position) {
        val toCell = world.map.getCell(position)
        if (toCell is Cell.StaticObject) {
            if (toCell.staticObject is ExitDoor) {
                world.map.moveCell(player.position, position)
                world.victory = true
            }
        }

        if (toCell is Cell.Item) {
            if (toCell.item is Sword) {
                val sword = toCell.item
                player.inventory.foundItem(sword)
            }
        }
    }
}

class MobActionProcessor {
    fun processMobAction(world: World, unit: GameUnit, action: MobAction) {
        if (unit !is Mob) {
            return
        }
        when {
            unit is CloneableMob && action is Clone -> {
                val positions = getCorrectMoves(unit.position, unit.moves, world.map).map { unit.position + it }
                if (positions.isNotEmpty()) {
                    val newMobPosition = positions.random()
                    val newId = world.idManager.getNextId()
                    unit.hp /= 2
                    val newMold = unit.clone(newId, newMobPosition)
                    world.map.setCell(newMold.position, Cell.Unit(newMold))
                    world.units[newMold.id] = newMold
                }
            }

            else -> {}
        }

    }

}

class InteractionProcessor {
    fun processInteract(world: World, player: GameUnit) {
        if (player is PlayerUnit) {
            return processInteractForPlayer(world, player)
        }
    }

    private fun processInteractForPlayer(world: World, player: PlayerUnit) {
        for (moveAction in player.moves) {
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
    }
}

class FightProcessor {
    fun processFight(world: World, attacker: GameUnit, defender: GameUnit) {
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

        processExtraEffects(attacker, defender, world)
    }

    private fun processExtraEffects(
        attacker: GameUnit,
        defender: GameUnit,
        world: World
    ) {
        if (attacker is PlayerUnit && attacker.isSwordEquipped() && defender is Mob) {
            val contusionStrategy = ContusionStrategy(defender.state.strategy)
            defender.state = GoodHealthState(contusionStrategy)
            val startTick = world.tick

            val contusionEffect = Effect { tick ->
                if (tick == startTick + 5) {
                    defender.state = GoodHealthState(contusionStrategy.baseStrategy)
                    true
                } else {
                    false
                }
            }

            world.effects.add(contusionEffect)
        }
    }
}