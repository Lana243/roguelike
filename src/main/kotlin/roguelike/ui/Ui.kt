package roguelike.ui

import roguelike.ui.views.View

abstract class Ui {
    abstract fun pollEvent(): Event
    abstract fun render(view: View)
}