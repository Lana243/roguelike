package roguelike.state.game.world.map

import roguelike.state.game.SCREEN_LENGTH_X
import roguelike.state.game.SCREEN_LENGTH_Y

/**
 * Класс, ответственный за создание различных игровых карт.
 */
class MapBuilder {

    /**
     * Считать карту из файла.
     */
    fun fromFile(path: String): MapBuilder {
        pathToMap = path
        return this
    }

    /**
     * Установить размеры карты.
     */
    fun setSize(lengthX: Int, lengthY: Int): MapBuilder {
        this.lengthX = lengthX
        this.lengthY = lengthY
        return this
    }

    /**
     * Случайно сгенерировать карту.
     */
    fun randomGenerate(): MapBuilder {
        shouldBeGenerated = true
        return this
    }

    /**
     * Применить все установленные настройки для создания карты.
     */
    fun build(): String {
        val mapFactory = if (pathToMap == null || shouldBeGenerated)
            MapRandomGenerator(lengthX, lengthY)
        else
            MapFromFileGenerator(pathToMap!!)

        return mapFactory.createMap()
    }

    // internal

    private var lengthX = SCREEN_LENGTH_X

    private var lengthY = SCREEN_LENGTH_Y - 1

    private var pathToMap: String? = null

    private var shouldBeGenerated = false
}