package roguelike.state.game.world

import roguelike.state.game.world.objects.Effect
import roguelike.state.game.world.objects.ExitDoor
import roguelike.state.game.world.objects.GameItem
import roguelike.state.game.world.objects.GameStaticObject
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.units.PlayerUnit
import roguelike.utility.IdManager
import java.util.SortedMap

/**
 * Вся информация об игре для одного уровня
 */
data class World(
    /**
     * карта поля уровня
     */
    val map: GameMap,
    /**
     * игрок
     */
    val player: PlayerUnit,
    /**
     * Статические объекты на карте
     */
    val staticObjects: Map<Int, GameStaticObject>,
    /**
     * Предметы
     */
    val items: Map<Int, GameItem>,
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
}