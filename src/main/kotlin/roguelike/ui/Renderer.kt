package roguelike.ui

import com.googlecode.lanterna.terminal.Terminal
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

/**
 * Класс для отображения [View]
 */
abstract class Renderer : ViewVisitor<Unit> {
    abstract fun render(view: View)
}

/**
 * Класс, реализующий отображение [View] при помощи библиотеки Lanterna
 */
class LanternaRenderer(
    private val terminal: Terminal
) : Renderer() {

    override fun render(view: View) {
        terminal.clearScreen()
        terminal.setCursorVisible(false)
        visitView(view)
        terminal.flush()
    }

    override fun visitView(view: View) {
        when (view) {
            is AsciiGrid -> visitAsciiGrid(view)
            is Composite -> visitComposite(view)
        }
    }

    override fun visitAsciiGrid(view: AsciiGrid) {
        for (line in view.grid) {
            terminal.putString(line)
            terminal.changeCursorPositionBy(deltaX = -line.length, deltaY = +1)
        }
    }

    override fun visitComposite(view: Composite) {
        val initialCursorPosition = terminal.cursorPosition
        for (child in view.children) {
            terminal.cursorPosition = initialCursorPosition
            terminal.changeCursorPositionBy(child.x, child.y)
            visitView(child.view)
        }
    }

    // internal

    private fun Terminal.changeCursorPositionBy(deltaX: Int, deltaY: Int) {
        setCursorPosition(cursorPosition.column + deltaX, cursorPosition.row + deltaY)
    }
}
