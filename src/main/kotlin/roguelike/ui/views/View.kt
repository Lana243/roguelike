package roguelike.ui.views

import roguelike.ui.ViewVisitor

interface View {
    fun <T> accept(visitor: ViewVisitor<T>): T
}