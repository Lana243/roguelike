package roguelike.controller.menu

import roguelike.controller.ViewBuilder
import roguelike.state.menu.MenuScreenState
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

class MenuViewBuilder : ViewBuilder<MenuScreenState> {
    override fun build(state: MenuScreenState): View {
        val playButtonView = AsciiGrid(listOf(state.playButtonText))
        val escButtonView = AsciiGrid(listOf(state.escButtonText))
        return Composite(listOf(
            Composite.ViewWithPosition(
                (state.screenLengthX - state.playButtonText.length) / 2,
                state.screenLengthY / 2 - 2,
                playButtonView
            ),
            Composite.ViewWithPosition(
                (state.screenLengthX - state.escButtonText.length) / 2,
                state.screenLengthY / 2 + 1,
                escButtonView
            )
        ))
    }
}