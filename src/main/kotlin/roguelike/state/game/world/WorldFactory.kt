package roguelike.state.game.world

import roguelike.state.game.*
import roguelike.state.game.world.map.*
import roguelike.state.game.world.objects.*
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.state.game.world.objects.units.mob.MobFactory
import roguelike.state.game.world.objects.units.mob.defaultMobFactory
import roguelike.utility.IdManager
import java.util.*

/**
 * Класс для создания мира.
 */
class WorldFactory(
    private val mapBuilder: MapBuilder,
    private val mobFactory: MobFactory = defaultMobFactory()
) {

    /**
     * Создает [World] по [MapFactory]
     */
    fun createWorld(): World {
        val map = mapBuilder.build()
        val gameMap = parseMap(map)
        return World(gameMap, player, staticObjects, items, units, idManager)
    }

    // internal

    private val idManager = IdManager()

    private lateinit var player: PlayerUnit

    private val staticObjects: MutableMap<Int, GameStaticObject> = mutableMapOf()

    private val items: MutableMap<Position, GameItem> = mutableMapOf()

    private val units: SortedMap<Int, GameUnit> = sortedMapOf()

    private fun parseMap(map: String): GameMap {
        val cells = mutableListOf<MutableList<Cell>>()
        val mapLines = map.filter { it != '\r' }.split('\n')
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
            CHAR_PLAYER -> {
                player = PlayerUnit(
                    idManager.playerId(),
                    Position(charIndex, lineIndex)
                )
                units += player.id to player
                Cell.Unit(player)
            }

            CHAR_DOOR -> {
                val exitDoor = ExitDoor(idManager.getNextId())
                Cell.StaticObject(exitDoor)
            }

            in listOf(CHAR_UNUSED_WELL, CHAR_USED_WELL) -> {
                val well = Well(idManager.getNextId())
                staticObjects += well.id to well
                Cell.StaticObject(well)
            }

            CHAR_APPLE -> {
                val apple = Apple(idManager.getNextId())
                items += Position(charIndex, lineIndex) to apple
                Cell.Item(apple)
            }

            CHAR_SWORD -> {
                val sword = Sword(idManager.getNextId())
                items += Position(charIndex, lineIndex) to sword
                Cell.Item(sword)
            }

            CHAR_PAWN -> {
                val mobPosition = Position(charIndex, lineIndex)
                val mob = mobFactory.getMob(idManager.getNextId(), mobPosition)
                units += mob.id to mob
                Cell.Unit(mob)
            }

            else -> Cell.Empty
        }
}
