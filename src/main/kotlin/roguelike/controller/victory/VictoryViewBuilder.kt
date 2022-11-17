package roguelike.controller.victory

import roguelike.controller.ViewBuilder
import roguelike.state.menu.MenuScreenState
import roguelike.state.victory.VictoryScreenState
import roguelike.ui.views.AsciiGrid
import roguelike.ui.views.Composite
import roguelike.ui.views.View

/**
 * Реализация интерфейса [ViewBuilder] для состояния [MenuScreenState]
 */
class VictoryViewBuilder : ViewBuilder<VictoryScreenState> {
    override fun build(state: VictoryScreenState): View {
        val quickPlayButtonView = AsciiGrid(listOf(state.wonText))
        val menuButtonView = AsciiGrid(listOf(state.menuButtonText))
        val escButtonView = AsciiGrid(listOf(state.escButtonText))
        return Composite(listOf(
            Composite.ViewWithPosition(
                (state.screenLengthX - state.wonText.length) / 2,
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