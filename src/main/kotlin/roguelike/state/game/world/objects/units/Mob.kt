package roguelike.state.game.world.objects.units

import roguelike.state.game.world.Position

data class Mob(
    override val id: Int,
    override val position: Position,
    override val attackRate: Int,
    override val maxHp: Int,
    override val hp: Int,
) : GameUnit()