package roguelike.utility

class IdManager {

    fun getNextId(): Int {
        lastId += 1
        return lastId
    }

    private var lastId: Int = -1
}