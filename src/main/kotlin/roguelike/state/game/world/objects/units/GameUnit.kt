package roguelike.state.game.world.objects.units

import roguelike.state.game.world.objects.GameObject
import roguelike.state.game.world.Position

/**
 * Персонаж игры. Игрок или моб
 */
abstract class GameUnit : GameObject() {
    /**
     * Текущая позиция [GameUnit]
     */
    abstract val position: Position

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
    abstract val hp: Int
}