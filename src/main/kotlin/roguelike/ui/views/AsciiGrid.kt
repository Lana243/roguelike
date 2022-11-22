package roguelike.ui.views

import roguelike.ui.ViewVisitor

/**
 * Поле, представленное в виде таблицы ASCII символов
 */
class AsciiGrid(val grid: List<String>) : View {
    override fun <T> accept(visitor: ViewVisitor<T>): T =
        visitor.visitAsciiGrid(this)
}