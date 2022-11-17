package roguelike.ui

import com.googlecode.lanterna.terminal.Terminal
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

/**
 * Класс для отображения [View]
 */
abstract class Renderer {
    /**
     * Функция для отображения [View]
     */
    abstract fun render(view: View)
}

/**
 * Класс, реализующий отображение [View] при помощи библиотеки Lanterna
 */
class LanternaRenderer(
    private val terminal: Terminal
) : Renderer(), ViewVisitor<Unit> {

    override fun render(view: View) {
        terminal.clearScreen()
        terminal.setCursorVisible(false)
        view.accept(this)
        terminal.flush()
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
            child.view.accept(this)
        }
    }

    // internal

    private fun Terminal.changeCursorPositionBy(deltaX: Int, deltaY: Int) {
        setCursorPosition(cursorPosition.column + deltaX, cursorPosition.row + deltaY)
    }
}
