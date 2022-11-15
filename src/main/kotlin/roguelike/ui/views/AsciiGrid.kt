package roguelike.ui.views

import roguelike.ui.ViewVisitor

class AsciiGrid(val grid: List<String>) : View {
    override fun <T> accept(visitor: ViewVisitor<T>): T =
        visitor.visitAsciiGrid(this)
}