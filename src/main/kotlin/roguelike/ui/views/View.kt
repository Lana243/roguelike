package roguelike.ui.views

import roguelike.ui.ViewVisitor

sealed interface View {
    fun <T> accept(visitor: ViewVisitor<T>): T
}