package roguelike.state.game.world

import roguelike.state.game.*
import roguelike.state.game.world.map.*
import roguelike.state.game.world.objects.*
import roguelike.state.game.world.objects.units.*
import roguelike.utility.IdManager
import java.util.SortedMap
import kotlin.random.Random

class WorldFactory(private val mapBuilder: MapBuilder) {

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

    private val items: MutableMap<Int, GameItem> = mutableMapOf()

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
                Cell.Item(apple)
            }

            CHAR_SWORD -> {
                val sword = Sword(idManager.getNextId())
                items += sword.id to sword
                Cell.Item(sword)
            }

            CHAR_PAWN -> {
                val mob = Pawn(
                    idManager.getNextId(),
                    Position(charIndex, lineIndex),
                    getRandomMobStrategy()
                )
                units += mob.id to mob
                Cell.Unit(mob)
            }

            else -> Cell.Empty
        }

    private val availableMobStrategies = listOf(
        PassiveStrategy(),
        AggressiveStrategy(),
        AvoidanceStrategy(),
    )

    private fun getRandomMobStrategy(): MobStrategy =
        availableMobStrategies[Random.nextInt(availableMobStrategies.size)]
}
