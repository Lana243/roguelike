package roguelike.ui.views

import roguelike.ui.ViewVisitor

/**
 * Поле, представленное в виде таблицы ASCII символов
 */
data class AsciiGrid(val grid: List<String>, val color: AsciiColor? = null) : View {

    override fun <T> accept(visitor: ViewVisitor<T>): T =
        visitor.visitAsciiGrid(this)

    companion object {

        fun fromChar(char: Char, color: AsciiColor? = null): AsciiGrid =
            fromString(char.toString(), color)

        fun fromString(string: String, color: AsciiColor? = null): AsciiGrid =
            AsciiGrid(listOf(string), color)
    }
}
