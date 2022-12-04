package roguelike.state.game.world.map

import java.io.File

class MapFromFileGenerator(private val fileName: String) : MapFactory {
    override fun createMap(): String = File(fileName).readText(Charsets.UTF_8)
}