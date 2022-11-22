package roguelike.controller

import roguelike.state.State
import roguelike.ui.views.View


/**
 * Интерфейс, реализующий архитектурный паттерн Builder,
 * позволяющий создать [View] по [State]
 */
interface ViewBuilder<T : State> {
    /**
     * Создает [View] по состоянию [state]
     */
    fun build(state: T): View
}