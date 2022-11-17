package roguelike.controller.menu

import roguelike.controller.Controller
import roguelike.state.Message
import roguelike.state.menu.MenuMessage
import roguelike.state.menu.MenuScreenState
import roguelike.ui.Event
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

/**
 * Контроллер, обеспечивающий взаимодействие UI и игровой логики, когда игрок находится на экране меню
 */
class MenuController : Controller<MenuScreenState> {

    override fun bindState(state: MenuScreenState): View {
        return menuViewBuilder.build(state)
    }

    override fun processEvent(event: Event): Message? {
        return when (event) {
            Event.KeyEnterPressed -> MenuMessage.StartGame
            Event.KeyEscPressed -> MenuMessage.Exit
            else -> null
        }
    }

    // internal

    private val menuViewBuilder = MenuViewBuilder()
}