package roguelike.utility

/**
 * Класс для создания новых id
 */
class IdManager {

    fun getNextId(): Int {
        lastId += 1
        return lastId
    }

    private var lastId: Int = -1
}