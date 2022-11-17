package roguelike.controller.game

import roguelike.controller.ViewBuilder
import roguelike.state.game.CHAR_EMPTY
import roguelike.state.game.CHAR_WALL
import roguelike.state.game.GameState
import roguelike.state.game.world.Cell
import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.Apple
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.Sword
import roguelike.state.game.world.objects.Well
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

class GameViewBuilder : ViewBuilder<GameState> {

    override fun build(state: GameState): View {
        val gameMap = state.world.map
        val (lengthX, lengthY) = gameMap.getSize()

        val childViews = mutableListOf<Composite.ViewWithPosition>()
        for (x in 0 until lengthX) {
            for (y in 0 until lengthY) {
                val cell = gameMap.getCell(Position(x, y))
                when (cell) {
                    Cell.Empty -> {}
                    else -> {
                        val charView = AsciiGrid(listOf(charByCell(x, y, cell).toString()))
                        childViews += Composite.ViewWithPosition(x, y, charView)
                    }
                }
            }
        }

        childViews += Composite.ViewWithPosition(
            x = 0,
            y = lengthY,
            AsciiGrid(listOf("HP: ${state.world.player.hp}  |  Attack: ${state.world.player.attackRate}"))
        )

        return Composite(childViews)
    }

    // internal

    private fun charByCell(x: Int, y: Int, cell: Cell): Char =
        when (cell) {
            Cell.Empty -> CHAR_EMPTY
            is Cell.Unit -> {
                when (cell.unit) {
                    is PlayerUnit -> 'P'
                    else -> '?'
                }
            }
            Cell.Wall -> CHAR_WALL
            is Cell.Item -> {
                when (cell.item) {
                    is Apple -> 'A'
                    is Sword -> 'S'
                }
            }
            is Cell.StaticObject -> {
                when (cell.staticObject) {
                    is ExitDoor -> 'D'
                    is Well -> 'W'
                }
            }
            else -> '?'
        }
}