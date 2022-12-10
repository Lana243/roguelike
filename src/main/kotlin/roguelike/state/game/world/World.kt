package roguelike.state.game.world

import roguelike.state.game.world.map.Cell
import roguelike.state.game.world.map.GameMap
import roguelike.state.game.world.objects.Effect
import roguelike.state.game.world.objects.GameItem
import roguelike.state.game.world.objects.GameStaticObject
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.utility.IdManager
import java.util.*

/**
 * Вся информация об игре для одного уровня.
 *
 * Мир мутабелен.
 */
class World(
    /**
     * Карта поля уровня
     */
    val map: GameMap,
    /**
     * Иигрок
     */
    val player: PlayerUnit,
    /**
     * Статические объекты на карте
     */
    val staticObjects: Map<Int, GameStaticObject>,
    /**
     * Предметы на земле
     */
    val items: MutableMap<Position, GameItem>,
    /**
     * Юниты
     */
    val units: SortedMap<Int, GameUnit>,
    /**
     * Вспомогательный класс для выдачи уникального `Id` объектам
     */
    val idManager: IdManager = IdManager()
) {
    /**
     * Текущее время в игре
     */
    var tick = 0

    /**
     * Выиграл ли игрок
     */
    var victory: Boolean = false

    /**
     * Проиграл ли игрок
     */
    val defeat: Boolean get() = player.hp <= 0

    /**
     * Продолжительные Эффекты
     */
    val effects: MutableSet<Effect> = mutableSetOf()

    fun killUnit(unit: GameUnit) {
        units.remove(unit.id)
        map.setCell(unit.position, Cell.Empty)
    }

    fun newUnit(unit: GameUnit) {
        units += unit.id to unit
        map.setCell(unit.position, Cell.Unit(unit))

    }

    fun decreaseUnitHp(unit: GameUnit, size: Int, kill: Boolean = true): Boolean {
        unit.updateHp(-size)

        if (unit.hp <= 0) {
            if (kill) {
                killUnit(unit)
                return true
            }

        }
        return false
    }

    fun moveUnit(unit: GameUnit, position: Position) {
        map.moveCell(unit.position, position)
        map.setCell(unit.position, Cell.Empty)
        unit.position = position
    }
}