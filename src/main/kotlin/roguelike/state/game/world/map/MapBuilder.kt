package roguelike.state.game.world.map

import roguelike.state.game.SCREEN_LENGTH_X
import roguelike.state.game.SCREEN_LENGTH_Y

class MapBuilder {

    fun fromFile(path: String): MapBuilder {
        pathToMap = path
        return this
    }

    fun setSize(lengthX: Int, lengthY: Int): MapBuilder {
        this.lengthX = lengthX
        this.lengthY = lengthY
        return this
    }

    fun randomGenerate(): MapBuilder {
        shouldBeGenerated = true
        return this
    }

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