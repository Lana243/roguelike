package roguelike.state.game.world

import roguelike.state.game.CHAR_EMPTY
import roguelike.state.game.CHAR_PLAYER
import roguelike.state.game.CHAR_WALL
import kotlin.random.Random

class MapGenerator(
    private val lengthX: Int,
    private val lengthY: Int
) : MapFactory {

    override fun createMap(): String {
        addBorder()
        addPlayer()
        for (i in 0..300) {
            addWalls()
        }
        return rawMap.joinToString("\n") { it.joinToString("") }
    }

    // internal

    private val rawMap = MutableList(lengthY) { MutableList(lengthX) { CHAR_EMPTY } }

    private fun addBorder() {
        for (y in 0 until lengthY) {
            rawMap[y][0] = CHAR_WALL
            rawMap[y][lengthX - 1] = CHAR_WALL
        }
        for (x in 0 until lengthX) {
            rawMap[0][x] = CHAR_WALL
            rawMap[lengthY - 1][x] = CHAR_WALL
        }
    }

    private fun addPlayer() {
        rawMap[2][2] = CHAR_PLAYER
    }

    private fun addWalls() {
        var totalWeight = 0
        for (y in 0 until lengthY) {
            for (x in 0 until lengthX) {
                if (rawMap[y][x] == CHAR_EMPTY) {
                    totalWeight += cellWeight(y, x)
                }
            }
        }
        val chosenNumber = Random.nextInt(totalWeight)

        var currentWeight = 0
        for (y in 0 until lengthY) {
            for (x in 0 until lengthX) {
                if (rawMap[y][x] == CHAR_EMPTY) {
                    currentWeight += cellWeight(y, x)
                }
                if (currentWeight >= chosenNumber) {
                    rawMap[y][x] = CHAR_WALL
                    break
                }
            }
            if (currentWeight >= chosenNumber) {
                break
            }
        }
    }

    private fun cellWeight(y: Int, x: Int): Int {
        return when (getNeighbours(y, x).size) {
            0 -> 1
            1 -> 1000
            2 -> 100000
            else -> 1000000
        }
    }

    private fun getNeighbours(y: Int, x: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for ((dy, dx) in listOf(1 to 1, 1 to -1, -1 to 1, -1 to -1)) {
            val (newY, newX) = y + dy to x + dx
            if (cellInBound(newY, newX)) {
                result += newY to newX
            }
        }
        return result
    }

    private fun cellInBound(y: Int, x: Int): Boolean {
        return y in 0 until lengthY && x in 0 until lengthX
    }
}