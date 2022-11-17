package roguelike.controller.menu

import roguelike.controller.ViewBuilder
import roguelike.state.menu.MenuScreenState
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

/**
 * Реализация интерфейса [ViewBuilder] для состояния [MenuScreenState]
 */
class MenuViewBuilder : ViewBuilder<MenuScreenState> {
    override fun build(state: MenuScreenState): View {
        val quickPlayButtonView = AsciiGrid(listOf(state.quickPlayButtonText))
        val level1ButtonView = AsciiGrid(listOf(state.level1ButtonText))
        val escButtonView = AsciiGrid(listOf(state.escButtonText))
        return Composite(listOf(
            Composite.ViewWithPosition(
                (state.screenLengthX - state.quickPlayButtonText.length) / 2,
                state.screenLengthY / 2 - 4,
                quickPlayButtonView
            ),
            Composite.ViewWithPosition(
                (state.screenLengthX - state.level1ButtonText.length) / 2,
                state.screenLengthY / 2 - 2,
                level1ButtonView
            ),
            Composite.ViewWithPosition(
                (state.screenLengthX - state.escButtonText.length) / 2,
                state.screenLengthY / 2 + 1,
                escButtonView
            )
        ))
    }
}