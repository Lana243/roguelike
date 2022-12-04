package roguelike.controller.victory

import roguelike.controller.ViewBuilder
import roguelike.state.game.SCREEN_LENGTH_X
import roguelike.state.game.SCREEN_LENGTH_Y
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
                (SCREEN_LENGTH_X - state.wonText.length) / 2,
                SCREEN_LENGTH_Y / 2 - 4,
                quickPlayButtonView
            ),
            Composite.ViewWithPosition(
                (SCREEN_LENGTH_X - state.menuButtonText.length) / 2,
                SCREEN_LENGTH_Y / 2 - 2,
                menuButtonView
            ),
            Composite.ViewWithPosition(
                (SCREEN_LENGTH_X - state.escButtonText.length) / 2,
                SCREEN_LENGTH_Y / 2 + 1,
                escButtonView
            )
        ))
    }
}