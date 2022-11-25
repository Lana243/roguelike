package roguelike.controller.game

import roguelike.controller.ViewBuilder
import roguelike.state.game.*
import roguelike.state.game.world.Cell
import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.Sword
import roguelike.state.game.world.objects.Well
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
                        val charView = viewByCell(x, y, cell)
                        childViews += Composite.ViewWithPosition(x, y, charView)

                        if (state.showInfo) {
                            infoViewByCell(cell)?.let {
                                infoViews += Composite.ViewWithPosition(x - 1, y - 1, it)
                            }
                        }
                    }
                }
            }
        }
        childViews += infoViews

        val inventory = "Inventory: " + state.world.player.inventory.items.joinToString {
            var c = if (it.item is Sword) {
                's'
            } else {
                '.'
            }
            if (it.state == Inventory.ItemData.State.EQUIPED) {
                c = c.uppercaseChar()
            }
            c.toString()
        }.padEnd(5, '.')

        childViews += Composite.ViewWithPosition(
            x = 0,
            y = lengthY,
            AsciiGrid(listOf("HP: ${state.world.player.hp}  |  Attack: ${state.world.player.attackRate}  |  Level: ${state.world.player.level}  |  " + inventory))
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
                    is Mob -> CHAR_MOB
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
                    is Well -> CHAR_WELL
                }
            }
            else -> CHAR_UNKNOWN
        }

    private fun viewByCell(x: Int, y: Int, cell: Cell): View =
        AsciiGrid(listOf(charByCell(x, y, cell).toString()))


    private fun infoViewByCell(cell: Cell): View? =
        when (cell) {
            is Cell.Unit -> {
                when (cell.unit) {
                    is Mob -> {
                        val hpView = AsciiGrid(listOf("${cell.unit.hp}"), AsciiColor.Green)
                        val attackView = AsciiGrid(listOf("${cell.unit.attackRate}"), AsciiColor.Red)
                        val infoView = Composite(listOf(
                            Composite.ViewWithPosition(0, 0, attackView),
                            Composite.ViewWithPosition(2, 0, hpView),
                        ))
                        infoView
                    }
                    else -> null
                }
            }
            else -> null
        }
}