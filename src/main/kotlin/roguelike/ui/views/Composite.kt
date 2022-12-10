package roguelike.ui.views

import roguelike.ui.ViewVisitor

/**
 * [View], состоящая из нескольних дочерних [View]
 *
 * @property children список дочерних [View] с их координатами относительно текущей [View]
 */
class Composite(
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

    companion object {
        /**
         * Ставит переданные [AsciiGrid] в горизонтальную линию друг за другом.
         */
        fun lineFromAscii(parts: List<AsciiGrid>): Composite {
            var nextXStart = 0
            val views = mutableListOf<ViewWithPosition>()
            for (i in parts.indices) {
                val part = parts[i]
                views += ViewWithPosition(nextXStart, 0, part)
                val length = part.grid.maxOf { it.length }
                nextXStart += length
            }
            return Composite(views)
        }
    }
}

/*
Сетка координат выглядит так:
  (0, 0) -- X -->
    |
    Y
    |
    v
*/
