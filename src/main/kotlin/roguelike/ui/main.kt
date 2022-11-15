package roguelike.ui

import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

// TODO: delete this file

fun main() {
    val renderer = LanternaRenderer()
    val box = AsciiGrid(listOf("01", "23"))
    val view = Composite(listOf(
        Composite.ViewWithPosition(10, 2, parseMap(map)),
        Composite.ViewWithPosition(40, 15, parseMap(map)),
        Composite.ViewWithPosition(20, 10, box),
        Composite.ViewWithPosition(60, 3, box),
        Composite.ViewWithPosition(7, 19, box),
    ))
    renderer.render(view)
}

val map = """
xxxxxxxxxxxxxxxxxxxxxxxxxxx
x          x              x
x     x            xxxxxxxx
xxxxxxxxxxxx             xx
x     xxx       xxxxxx    x
xx                        x
xxxxxxxxxxxxxxxxxxxxxxxxxxx
""".drop(1)

fun parseMap(map: String): View {
    val mapLines = map.split('\n')
    val charViews = mutableListOf<Composite.ViewWithPosition>()
    for (lineIndex in mapLines.indices) {
        for (charIndex in mapLines[lineIndex].indices) {
            val char = mapLines[lineIndex][charIndex]
            if (char == 'x') {
                val charView = AsciiGrid(listOf(char.toString()))
                charViews += Composite.ViewWithPosition(charIndex, lineIndex, charView) // (!) swap
            }
        }
    }
    return Composite(charViews)
}