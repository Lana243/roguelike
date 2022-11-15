package roguelike

import roguelike.controller.Controller
import roguelike.state.State
import roguelike.ui.Ui

class Engine {
    fun run() {
        val ui: Ui = TODO()
        val controller: Controller<State> = TODO()
        var currentState: State = TODO()

        while (true) {
            val view = controller.bindState(currentState)
            ui.render(view)
            val event = ui.pollEvent()
            val message = controller.processEvent(event)
            currentState = currentState.process(message)
        }
    }
}