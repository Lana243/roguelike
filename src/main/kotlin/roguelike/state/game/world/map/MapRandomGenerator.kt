package roguelike.state.game.world.map

import roguelike.state.game.*
import kotlin.math.min
import kotlin.math.max
import kotlin.random.Random

/**
 * Случайно генерирует карту.
 */
class MapRandomGenerator(
    private val lengthX: Int,
    private val lengthY: Int
) : MapFactory {

    override fun createMap(): String {
        dfs(1, 1)
        addRandomStamps()
        addPlayer()
        addDoor()
        addMobs()
        addSword()
        addApples()
        addWells()
        return rawMap.joinToString("\n") { it.joinToString("") }
    }

    // internal

    private val rawMap = MutableList(lengthY) { MutableList(lengthX) { CHAR_WALL } }

    private fun getRandomEmptyCell(): Pair<Int, Int> {
        var (y, x) = -1 to -1
        while (!cellInsideBorder(y, x) || rawMap[y][x] != CHAR_EMPTY) {
            y = Random.nextInt(1, lengthY - 1)
            x = Random.nextInt(1, lengthX - 1)
        }
        return y to x
    }

    private fun addPlayer() {
        makeStamp(1, 5, 1, 8)
        rawMap[2][2] = CHAR_PLAYER
    }

    private fun addDoor() {
        makeStamp(lengthY - 5, lengthY - 1, lengthX - 7, lengthX - 1)
        rawMap[lengthY - 3][lengthX - 3] = CHAR_DOOR
    }

    private fun addMobs(count: Int = 15) =
        addCharsToEmptyCell(count, CHAR_PAWN)

    private fun addSword() =
        addCharsToEmptyCell(count = 1, CHAR_SWORD)

    private fun addApples(count: Int = 25) =
        addCharsToEmptyCell(count, CHAR_APPLE)

    private fun addWells(count: Int = 3) =
        addCharsToEmptyCell(count, CHAR_UNUSED_WELL)

    private fun addCharsToEmptyCell(count: Int, char: Char) {
        for (i in 0 until count) {
            val (y, x) = getRandomEmptyCell()
            rawMap[y][x] = char
        }
    }

    private fun addRandomStamps(count: Int = 10) {
        val yUps = mutableListOf(Random.nextInt(1, 3))
        val xLefts = mutableListOf(Random.nextInt(1, 8))
        for (i in 0 until count - 1) {
            xLefts += xLefts.last() + Random.nextInt(6, 10)
            yUps += yUps.last() + Random.nextInt(1, 4)
        }
        yUps.shuffle()
        xLefts.shuffle()
        for (i in 0 until count) {
            val yDown = yUps[i] + Random.nextInt(5, 8)
            val xRight = xLefts[i] + Random.nextInt(6, 13)
            makeStamp(yUps[i], yDown, xLefts[i], xRight)
        }
    }

    private val visited = MutableList(lengthY) { MutableList(lengthX) { false } }

    private fun dfs(y: Int, x: Int) {
        visited[y][x] = true
        for ((nextY, nextX) in getNeighbours(y, x).shuffled()) {
            if (visited[nextY][nextX]) {
                continue
            }
            for (yi in min(y, nextY)..max(y, nextY) step 2) {
                for (xi in min(x, nextX)..max(x, nextX) step 2) {
                    for (dy in 0..1) {
                        for (dx in 0..1) {
                            if (cellInsideBorder(yi + dy, xi + dx)) {
                                rawMap[yi + dy][xi + dx] = CHAR_EMPTY
                            }
                        }
                    }
                }
            }
            dfs(nextY, nextX)
        }
    }

    private fun makeStamp(yUp: Int, yDown: Int, xLeft: Int, xRight: Int) {
        for (y in yUp..yDown) {
            for (x in xLeft..xRight) {
                if (cellInsideBorder(y, x)) {
                    rawMap[y][x] = CHAR_EMPTY
                }
            }
        }
    }

    private fun getNeighbours(y: Int, x: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for ((dy, dx) in listOf(0 to 4, 0 to -4, -4 to 0, 4 to 0)) {
            val (newY, newX) = y + dy to x + dx
            if (cellInsideBorder(newY, newX)) {
                result += newY to newX
            }
        }
        return result
    }

    private fun cellInsideBorder(y: Int, x: Int): Boolean {
        return y in 1 until lengthY - 1 && x in 1 until lengthX - 1
    }
}