package roguelike.ui

sealed interface Event {
    object KeyEnterPressed : Event
    object KeyEscPressed : Event

    object KeyLeftPressed : Event
    object KeyRightPressed : Event
    object KeyUpPressed : Event
    object KeyDownPressed : Event
}
