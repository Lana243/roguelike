package roguelike.state.game.world

import roguelike.state.game.CHAR_EMPTY
import roguelike.state.game.CHAR_WALL
import roguelike.state.game.world.objects.*
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.utility.IdManager

class WorldFactory(private val mapFactory: MapFactory) {

    fun createWorld(): World {
        val map = mapFactory.createMap()
        val gameMap = parseMap(map)
        return World(gameMap, player, staticObjects, items, idManager)
    }

    // internal

    private val idManager = IdManager()

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
            CHAR_EMPTY -> Cell.Empty
            CHAR_WALL -> Cell.Wall
            'P' -> {
                player = PlayerUnit(
                    idManager.getNextId(),
                    Position(charIndex, lineIndex)
                )
                Cell.Unit(player)
            }
            'D' -> {
                val exitDoor = ExitDoor(idManager.getNextId())
                Cell.StaticObject(exitDoor)
            }
            'W' -> {
                val well = Well(idManager.getNextId())
                Cell.StaticObject(well)
            }
            'A' -> {
                val apple = Apple(idManager.getNextId())
                Cell.Item(apple)
            }
            'S' -> {
                val sword = Sword(idManager.getNextId())
                Cell.Item(sword)
            }
            else -> Cell.Empty
        }
}
