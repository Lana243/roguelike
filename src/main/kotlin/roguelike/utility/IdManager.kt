package roguelike.utility

/**
 * Класс для создания новых id
 */
class IdManager {

    fun playerId(): Int = 0
    fun getNextId(): Int = ++lastId
    private var lastId: Int = 0
}