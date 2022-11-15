package roguelike.ui.views

import roguelike.ui.ViewVisitor

class Composite(
    val childs: List<View>
) : View {
    override fun <T> accept(visitor: ViewVisitor<T>): T =
        visitor.visitComposite(this)
}