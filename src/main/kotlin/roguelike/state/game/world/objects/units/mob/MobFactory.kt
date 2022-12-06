package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.*
import roguelike.utility.IdManager

interface MobFactory {
    fun getMob(position: Position, strategy: MobStrategy): Mob
}

class PawnFactory(private val idManager: IdManager) : MobFactory {
    override fun getMob(position: Position, strategy: MobStrategy): Mob =
        Pawn(idManager.getNextId(), position, strategy)
}

class KnightFactory(private val idManager: IdManager) : MobFactory {
    override fun getMob(position: Position, strategy: MobStrategy): Mob =
        Knight(idManager.getNextId(), position, strategy)
}

class RandomMobFactory(private val factories: List<MobFactory>) : MobFactory {
    override fun getMob(position: Position, strategy: MobStrategy): Mob =
        factories.random().getMob(position, strategy)
}