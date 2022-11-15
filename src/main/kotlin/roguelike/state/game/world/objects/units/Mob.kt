package roguelike.state.game.world.objects.units

import roguelike.state.game.world.Position

data class Mob(
    override val position: Position,
    override val attackRate: UInt,
    override val maxHp: UInt,
    override val hp: UInt,
) : GameUnit()