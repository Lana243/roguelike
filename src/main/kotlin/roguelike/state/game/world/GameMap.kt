package roguelike.state.game.world

interface GameMap {
    fun getCell(position: Position): Cell
}

sealed interface Cell {
    object Empty : Cell

    @JvmInline
    value class CellWithUnit(
        val unitId: Int
    ) : Cell

    @JvmInline
    value class CellWithItem(
        val itemId: Int
    ) : Cell

    @JvmInline
    value class CellWithStaticObject(
        val objectId: Int
    ) : Cell
}

class GameMapImpl: GameMap {
    private val map: Map<Position, Cell> = emptyMap()

    override fun getCell(position: Position): Cell = map[position]!!
}