package roguelike.ui

import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import roguelike.ui.views.View

abstract class Ui {
    /**
     * Преобразовывает команды, считанные с клавиатуры в [Event]
     */
    abstract fun pollEvent(): Event

    /**
     * Отображение view на экране
     */
    abstract fun render(view: View)
}


/**
 * UI реализованный на основе библиотеки Lanterna
 */
class LanternaUi : Ui() {

    override fun pollEvent(): Event {
        var event: Event? = null
        while (event == null) {
            val keyStroke = terminal.readInput() // blocking operation
            event = when (keyStroke.keyType) {
                KeyType.Enter -> Event.KeyEnterPressed
                KeyType.Escape -> Event.KeyEscPressed
                KeyType.F1 -> Event.KeyF1Pressed
                KeyType.F2 -> Event.KeyF2Pressed
                KeyType.ArrowLeft -> Event.KeyLeftPressed
                KeyType.ArrowRight -> Event.KeyRightPressed
                KeyType.ArrowUp -> Event.KeyUpPressed
                KeyType.ArrowDown -> Event.KeyDownPressed
                KeyType.Character -> Event.LetterOrDigitKeyPressed(keyStroke.character)
                else -> null
            }
        }
        return event
    }

    override fun render(view: View) {
        renderer.render(view)
    }

    // internal

    private val terminal = DefaultTerminalFactory().createTerminal()

    private val renderer = LanternaRenderer(terminal)
}