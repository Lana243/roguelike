package roguelike.controller.game

import roguelike.controller.ViewBuilder
import roguelike.state.game.*
import roguelike.state.game.world.map.Cell
import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.Sword
import roguelike.state.game.world.objects.Well
import roguelike.state.game.world.objects.units.ContusionStrategy
import roguelike.state.game.world.objects.units.Inventory
import roguelike.state.game.world.objects.units.Mob
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.ui.views.AsciiColor
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

/**
 * Класс, реализующий интерфейс [ViewBuilder] для [GameState]
 */
class GameViewBuilder : ViewBuilder<GameState> {

    override fun build(state: GameState): View {
        val gameMap = state.world.map
        val (lengthX, lengthY) = gameMap.getSize()

        val childViews = mutableListOf<Composite.ViewWithPosition>()
        val infoViews = mutableListOf<Composite.ViewWithPosition>()
        for (x in 0 until lengthX) {
            for (y in 0 until lengthY) {
                when (val cell = gameMap.getCell(Position(x, y))) {
                    Cell.Empty -> {}
                    else -> {
                        val charView = viewByCell(x, y, cell, state)
                        childViews += Composite.ViewWithPosition(x, y, charView)
                    }
                }
            }
        }
        childViews += infoViews

        val inventory = state.world.player.inventory.items.joinToString {
            var c = if (it.item is Sword) {
                's'
            } else {
                '.'
            }
            if (it.state == Inventory.ItemData.State.EQUIPPED) {
                c = c.uppercaseChar()
            }
            c.toString()
        }.padEnd(5, '.')

        childViews += Composite.ViewWithPosition(
            x = 0,
            y = lengthY,
            Composite.lineFromAscii(listOf(
                AsciiGrid.fromString("HP: "),
                AsciiGrid.fromString("${state.world.player.hp}", AsciiColor.Green),
                AsciiGrid.fromString("  |  "),
                AsciiGrid.fromString("Attack: "),
                AsciiGrid.fromString("${state.world.player.attackRate}", AsciiColor.Red),
                AsciiGrid.fromString("  |  "),
                AsciiGrid.fromString("Level: "),
                AsciiGrid.fromString("${state.world.player.level}", AsciiColor.White),
                AsciiGrid.fromString("  |  "),
                AsciiGrid.fromString("Exp: "),
                AsciiGrid.fromString("${state.world.player.exp}", AsciiColor.White),
                AsciiGrid.fromString("  |  "),
                AsciiGrid.fromString("Inventory: "),
                AsciiGrid.fromString(inventory, AsciiColor.White),
            ))
        )

        return Composite(childViews)
    }

    // internal

    private fun charByCell(x: Int, y: Int, cell: Cell): Char =
        when (cell) {
            Cell.Empty -> CHAR_EMPTY
            is Cell.Unit -> {
                when (cell.unit) {
                    is PlayerUnit -> CHAR_PLAYER
                    is Mob -> if (cell.unit.strategy is ContusionStrategy) CHAR_CONTUSED_MOB else CHAR_MOB
                    else -> CHAR_UNKNOWN
                }
            }
            Cell.Wall -> CHAR_WALL
            is Cell.Item -> {
                when (cell.item) {
                    is Apple -> CHAR_APPLE
                    is Sword -> CHAR_SWORD
                }
            }
            is Cell.StaticObject -> {
                when (cell.staticObject) {
                    is ExitDoor -> CHAR_DOOR
                    is Well -> if (cell.staticObject.visited) CHAR_USED_WELL else CHAR_UNUSED_WELL
                }
            }
            else -> CHAR_UNKNOWN
        }

    private fun viewByCell(x: Int, y: Int, cell: Cell, state: GameState): View {
        val char = charByCell(x, y, cell)
        val defaultItem = AsciiGrid.fromChar(char)
        return when (cell) {
            is Cell.Unit -> {
                when (cell.unit) {
                    is Mob -> {
                        if (state.settings.showMobsHp) {
                            AsciiGrid(listOf("${cell.unit.hp}"), AsciiColor.Green)
                        } else if (state.settings.showMobsAttackRate) {
                            AsciiGrid(listOf("${cell.unit.attackRate}"), AsciiColor.Red)
                        } else {
                            defaultItem.copy(color = AsciiColor.RedNice)
                        }
                    }
                    is PlayerUnit -> defaultItem.copy(color = AsciiColor.White)
                    else -> defaultItem
                }
            }
            is Cell.Item -> {
                when (cell.item) {
                    is Apple -> defaultItem.copy(color = AsciiColor.GreenNice)
                    is Sword -> defaultItem.copy(color = AsciiColor.YellowNice)
                }
            }
            is Cell.StaticObject -> {
                when (cell.staticObject) {
                    is Well -> defaultItem.copy(color = AsciiColor.BlueNice)
                    is ExitDoor -> defaultItem.copy(color = AsciiColor.Brown)
                }
            }
            else -> defaultItem
        }
    }
}