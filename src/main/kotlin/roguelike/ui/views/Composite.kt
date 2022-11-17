package roguelike.ui.views

import roguelike.ui.ViewVisitor

/**
 * [View], состоящая из нескольних дочерних [View]
 */
class Composite(
    /**
     * список дочерних [View] с их координатами относительно текущей [View]
     */
    val children: List<ViewWithPosition>
) : View {
    override fun <T> accept(visitor: ViewVisitor<T>): T =
        visitor.visitComposite(this)

    /**
     * [View] с координатами
     */
    data class ViewWithPosition(
        val x: Int,
        val y: Int,
        val view: View,
    )
}

/*
Сетка координат выглядит так:
  (0, 0) -- X -->
    |
    Y
    |
    v
*/
