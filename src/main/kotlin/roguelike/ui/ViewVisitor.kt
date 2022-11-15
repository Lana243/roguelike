package roguelike.ui

import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.Text

interface ViewVisitor<T> {
    fun visitAsciiGrid(view: AsciiGrid): T
    fun visitComposite(view: Composite): T
    fun visitText(text: Text): T
}