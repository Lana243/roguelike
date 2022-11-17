package roguelike.controller

import roguelike.state.Message
import roguelike.state.State
import roguelike.ui.Event
import roguelike.ui.views.View

interface Controller<T : State> {
    fun bindState(state: T): View
    fun processEvent(event: Event): Message?
}