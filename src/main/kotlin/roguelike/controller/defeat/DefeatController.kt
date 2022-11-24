package roguelike.controller.defeat

import roguelike.controller.Controller
import roguelike.state.Message
import roguelike.state.defeat.DefeatMessage
import roguelike.state.defeat.DefeatScreenState
import roguelike.ui.Event
import roguelike.ui.views.View

/**
 * Контроллер, обеспечивающий взаимодействие UI и игровой логики, когда игрок находится на экране меню
 */
class DefeatController : Controller<DefeatScreenState> {

    override fun bindState(state: DefeatScreenState): View {
        return defeatViewBuilder.build(state)
    }

    override fun processEvent(event: Event): Message? {
        return when (event) {
            Event.KeyF1Pressed -> DefeatMessage.MainMenu
            Event.KeyEscPressed -> DefeatMessage.Exit
            else -> null
        }
    }

    // internal

    private val defeatViewBuilder = DefeatViewBuilder()
}