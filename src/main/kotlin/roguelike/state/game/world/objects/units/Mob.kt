package roguelike.state.game.world.objects.units

import roguelike.state.game.world.Position

/**
 * Моб
 */
data class Mob(
    override val id: Int,
    override var position: Position,
    var strategy: MobStrategy,
    override val attackRate: Int = 2,
    override var maxHp: Int = 3,
    override var hp: Int = 3
) : GameUnit()