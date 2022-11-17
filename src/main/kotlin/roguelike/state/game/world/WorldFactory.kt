package roguelike.state.game.world

import roguelike.state.game.world.objects.GameItem
import roguelike.state.game.world.objects.GameStaticObject
import roguelike.state.game.world.objects.units.PlayerUnit

class WorldFactory(private val mapFactory: MapFactory) {
    fun createWorld(): World {
        val map = mapFactory.createMap()
        val gameMap = parseMap(map)
        return World(gameMap, player, staticObjects, items)
    }

    // internal

    private lateinit var player: PlayerUnit

    private val staticObjects: MutableMap<Int, GameStaticObject> = mutableMapOf()

    private val items: MutableMap<Int, GameItem> = mutableMapOf()

    private fun parseMap(map: String): GameMap {
        val cells = mutableListOf<MutableList<Cell>>()
        val mapLines = map.split('\n')
        for (lineIndex in mapLines.indices) {
            cells += mutableListOf<Cell>()
            val line = mapLines[lineIndex]
            for (charIndex in line.indices) {
                val char = line[charIndex]
                cells.last() += parseCell(lineIndex, charIndex, char)
            }
        }
        return GameMapImpl(cells.map { it.toTypedArray() }.toTypedArray())
    }

    private fun parseCell(lineIndex: Int, charIndex: Int, char: Char): Cell =
        when (char) {
            ' ' -> Cell.Empty
            '#' -> Cell.Wall
            'P' -> {
                player = PlayerUnit(
                    Position(charIndex, lineIndex)
                )
                Cell.Unit(player)
            }
            else -> Cell.Empty
        }
}
