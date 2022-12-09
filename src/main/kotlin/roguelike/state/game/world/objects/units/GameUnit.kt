package roguelike.state.game.world.objects.units

import roguelike.state.game.simulator.MoveAction
import roguelike.state.game.world.objects.GameObject
import roguelike.state.game.world.Position
import kotlin.math.max
import kotlin.math.min

/**
 * Персонаж игры. Игрок или моб
 */
abstract class GameUnit : GameObject() {
    /**
     * Текущая позиция [GameUnit]
     */
    abstract var position: Position

    /**
     * Возможные передвижения [GameUnit]
     */
    abstract val moves: List<MoveAction>

    /**
     * Уровень атаки [GameUnit]
     */
    abstract val attackRate: Int

    /**
     * Максимально здоровье, которое может быть у данного [GameUnit]
     */
    abstract val maxHp: Int

    /**
     * Текущее здоровье [GameUnit]
     */
    abstract var hp: Int

    /**
     * Изменяет здоровье так, чтобы оно не становилось больше максимально возможного и меньше 0
     */
    open fun updateHp(deltaHp: Int) {
        hp = max(0, min(maxHp, hp + deltaHp))
    }
}