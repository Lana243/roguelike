package roguelike.state.game.world.map

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.GameItem
import roguelike.state.game.world.objects.units.GameUnit
import roguelike.state.game.world.objects.GameStaticObject

interface GameMap {

    fun getSize(): Pair<Int, Int>

    fun getCell(position: Position): Cell

    fun setCell(position: Position, newCell: Cell)

    fun moveCell(from: Position, to: Position)

    fun isInsideMap(position: Position): Boolean
}

sealed interface Cell {

    /**
     * клетка, через которую нельзя пройти игроку
     */
    interface Solid : Cell

    object Empty : Cell

    object Wall : Cell, Solid

    @JvmInline
    value class Unit(val unit: GameUnit) : Cell, Solid

    @JvmInline
    value class Item(val item: GameItem) : Cell

    @JvmInline
    value class StaticObject(val staticObject: GameStaticObject) : Cell, Solid
}

class GameMapImpl(
    private val map: Array<Array<Cell>>
) : GameMap {

    override fun getSize(): Pair<Int, Int> =
        Pair(map[0].size, map.size)

    override fun getCell(position: Position): Cell =
        map[position.y][position.x]

    override fun setCell(position: Position, newCell: Cell) {
        map[position.y][position.x] = newCell
    }

    override fun moveCell(from: Position, to: Position) {
        setCell(to, getCell(from))
        setCell(from, Cell.Empty)
    }

    override fun isInsideMap(position: Position): Boolean =
        position.x >= 0 && position.x < map[0].size && position.y >= 0 && position.y < map.size
}