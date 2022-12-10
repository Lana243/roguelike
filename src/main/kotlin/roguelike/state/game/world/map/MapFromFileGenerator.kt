package roguelike.state.game.world.map

import java.io.File

/**
 * Считывает карту из файла [fileName].
 */
class MapFromFileGenerator(private val fileName: String) : MapFactory {
    override fun createMap(): String = File(fileName).readText(Charsets.UTF_8)
}
