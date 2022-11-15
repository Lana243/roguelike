package roguelike.ui.views

import roguelike.ui.ViewVisitor

class Composite(
    val children: List<ViewWithPosition>
) : View {
    override fun <T> accept(visitor: ViewVisitor<T>): T =
        visitor.visitComposite(this)

    data class ViewWithPosition(
        val x: Int,
        val y: Int,
        val view: View,
    )
}

/*
  (0, 0) -- X -->
    |
    Y
    |
    v
*/
