package roguelike.state.menu

import roguelike.state.Message

sealed interface MenuMessage : Message {
    object StartGame : MenuMessage
    object Exit : MenuMessage
}

