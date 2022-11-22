package roguelike.controller

import roguelike.state.Message
import roguelike.state.State
import roguelike.ui.Event
import roguelike.ui.views.View

/**
 * контроллер обеспечивает взаимодействие UI слоя со слоем игровой логики
 */
interface Controller<T : State> {
    /**
     * преобразует [State] во [View]
     * взаимодействие в сторону от игровой логики к UI
     */
    fun bindState(state: T): View

    /**
     * преобразует [Event] в [Message]
     * взаимодействие в сторону от UI к игровой логике
     */
    fun processEvent(event: Event): Message?
}