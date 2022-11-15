package roguelike.ui.views

import roguelike.ui.ViewVisitor

class AsciiGrid : View {
    override fun <T> accept(visitor: ViewVisitor<T>): T =
        visitor.visitAsciiGrid(this)
}