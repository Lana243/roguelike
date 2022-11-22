package roguelike.ui.views

import roguelike.ui.ViewVisitor


/**
 * Абстракция для визуального элемента
 */
sealed interface View {
    fun <T> accept(visitor: ViewVisitor<T>): T
}