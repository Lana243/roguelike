package roguelike.state.game.world

interface GameMap {
    fun getCell(position: Position): Cell
}

sealed interface Cell {
    object Empty : Cell

    object CellWithWall : Cell

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

class GameMapImpl(
    private val map: Array<Array<Cell>>
) : GameMap {
    override fun getCell(position: Position): Cell = map[position.x][position.y]
}