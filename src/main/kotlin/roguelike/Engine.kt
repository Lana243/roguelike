package roguelike

import roguelike.controller.Controller
import roguelike.controller.MainController
import roguelike.state.State
import roguelike.state.menu.MenuScreenState
import roguelike.ui.LanternaUi
import roguelike.ui.Ui

class Engine {
    fun run() {
        var currentState: State = MenuScreenState()
        val controller: Controller<State> = MainController(currentState)
        val ui: Ui = LanternaUi()

        while (true) {
            val view = controller.bindState(currentState)
            ui.render(view)
            val event = ui.pollEvent()
            val message = controller.processEvent(event)
            if (message != null) {
                currentState = currentState.process(message)
            }
        }
    }
}