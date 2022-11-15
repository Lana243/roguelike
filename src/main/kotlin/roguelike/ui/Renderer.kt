package roguelike.ui

import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

abstract class Renderer : ViewVisitor<Unit> {
    abstract fun render(view: View)
}

class LanternaRenderer : Renderer() {

    override fun render(view: View) {
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
            render(child.view)
        }
    }

    // internal

    private val terminal = DefaultTerminalFactory().createTerminal()

    private fun Terminal.changeCursorPositionBy(deltaX: Int, deltaY: Int) {
        setCursorPosition(cursorPosition.column + deltaX, cursorPosition.row + deltaY)
    }
}
