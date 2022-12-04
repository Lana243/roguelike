package roguelike.state.game.world.map

import java.io.File

class MapFromFileGenerator(private val fileName: String) : MapFactory {
    override fun createMap(): String = File(fileName).readText(Charsets.UTF_8)
}

class MapLevel1 : MapFactory {

    override fun createMap() = realMap.createMap()

    private val realMap = MapFromFileGenerator("src/main/resources/map-level-1.txt")
}