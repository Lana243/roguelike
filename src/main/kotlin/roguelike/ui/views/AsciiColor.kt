package roguelike.ui.views

/**
 * Цвет на игровом поле.
 */
enum class AsciiColor(val r: Int, val g: Int, val b: Int) {
    White(255, 255, 255),
    Red(255, 0, 0),
    Green(0, 255, 0),
    Brown(124, 71, 14),
    Purple(255, 51, 255),

    RedNice(233, 103, 103),
    GreenNice(139, 204, 0),
    BlueNice(45, 147, 210),
    YellowNice(242, 245, 180),
}
