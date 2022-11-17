package roguelike.ui

// низкоуровневое описание действия, сделанного пользователем
sealed interface Event {
    object KeyEnterPressed : Event
    object KeyEscPressed : Event

    object KeyF1Pressed : Event

    object KeyLeftPressed : Event
    object KeyRightPressed : Event
    object KeyUpPressed : Event
    object KeyDownPressed : Event
}
