package roguelike.ui

import roguelike.ui.views.View

abstract class Renderer : ViewVisitor<Unit> {
    abstract fun render(view: View)
}