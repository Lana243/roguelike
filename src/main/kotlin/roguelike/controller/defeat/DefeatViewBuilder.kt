package roguelike.controller.defeat

import roguelike.controller.ViewBuilder
import roguelike.state.defeat.DefeatScreenState
import roguelike.state.menu.MenuScreenState
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

/**
 * Реализация интерфейса [ViewBuilder] для состояния [MenuScreenState]
 */
class DefeatViewBuilder : ViewBuilder<DefeatScreenState> {
    override fun build(state: DefeatScreenState): View {
        val quickPlayButtonView = AsciiGrid(listOf(state.defeatText))
        val menuButtonView = AsciiGrid(listOf(state.menuButtonText))
        val escButtonView = AsciiGrid(listOf(state.escButtonText))
        return Composite(listOf(
            Composite.ViewWithPosition(
                (state.screenLengthX - state.defeatText.length) / 2,
                state.screenLengthY / 2 - 4,
                quickPlayButtonView
            ),
            Composite.ViewWithPosition(
                (state.screenLengthX - state.menuButtonText.length) / 2,
                state.screenLengthY / 2 - 2,
                menuButtonView
            ),
            Composite.ViewWithPosition(
                (state.screenLengthX - state.escButtonText.length) / 2,
                state.screenLengthY / 2 + 1,
                escButtonView
            )
        ))
    }
}