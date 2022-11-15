package roguelike.controller

import roguelike.state.State
import roguelike.ui.views.View

interface ViewBuilder<T : State> {
    fun build(state: T): View
}