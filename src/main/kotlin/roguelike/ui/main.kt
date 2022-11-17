package roguelike.ui

import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

// TODO: delete this file

fun main() {
    val terminal = DefaultTerminalFactory().createTerminal()
    val renderer = LanternaRenderer(terminal)
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
    return AsciiGrid(mapLines)
}