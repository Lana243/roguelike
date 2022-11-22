package roguelike.controller.victory

import roguelike.controller.Controller
import roguelike.state.Message
import roguelike.state.victory.VictoryMessage
import roguelike.state.victory.VictoryScreenState
import roguelike.ui.Event
import roguelike.ui.views.View

/**
 * Контроллер, обеспечивающий взаимодействие UI и игровой логики, когда игрок находится на экране меню
 */
class VictoryController : Controller<VictoryScreenState> {

    override fun bindState(state: VictoryScreenState): View {
        return victoryViewBuilder.build(state)
    }

    override fun processEvent(event: Event): Message? {
        return when (event) {
            Event.KeyF1Pressed -> VictoryMessage.MainMenu
            Event.KeyEscPressed -> VictoryMessage.Exit
            else -> null
        }
    }

    // internal

    private val victoryViewBuilder = VictoryViewBuilder()
}