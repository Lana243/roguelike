package roguelike.controller.menu

import roguelike.controller.ViewBuilder
import roguelike.state.game.SCREEN_LENGTH_X
import roguelike.state.game.SCREEN_LENGTH_Y
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
        val level2ButtonView = AsciiGrid(listOf(state.level2ButtonText))
        val escButtonView = AsciiGrid(listOf(state.escButtonText))
        return Composite(
            listOf(
                Composite.ViewWithPosition(
                    (SCREEN_LENGTH_X - state.quickPlayButtonText.length) / 2,
                    SCREEN_LENGTH_Y / 2 - 5,
                    quickPlayButtonView
                ),
                Composite.ViewWithPosition(
                    (SCREEN_LENGTH_X - state.level1ButtonText.length) / 2,
                    SCREEN_LENGTH_Y / 2 - 2,
                    level1ButtonView
                ),
                Composite.ViewWithPosition(
                    (SCREEN_LENGTH_X - state.level2ButtonText.length) / 2,
                    SCREEN_LENGTH_Y / 2,
                    level2ButtonView
                ),
                Composite.ViewWithPosition(
                    (SCREEN_LENGTH_X - state.escButtonText.length) / 2,
                    SCREEN_LENGTH_Y / 2 + 3,
                    escButtonView
                )
            )
        )
    }
}