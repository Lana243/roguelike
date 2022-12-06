package roguelike.state.game.world.objects.units.mob

import roguelike.state.game.world.Position
import roguelike.state.game.world.objects.units.*
import roguelike.utility.IdManager

interface MobFactory {
    fun getAggressiveMob(position: Position): Mob
    fun getPassiveMob(position: Position): Mob
    fun getAvoidingMob(position: Position): Mob
}

class PawnFactory(private val idManager: IdManager) : MobFactory {
    override fun getAggressiveMob(position: Position): Mob = Pawn(idManager.getNextId(), position, AggressiveStrategy())

    override fun getPassiveMob(position: Position): Mob = Pawn(idManager.getNextId(), position, PassiveStrategy())

    override fun getAvoidingMob(position: Position): Mob = Pawn(idManager.getNextId(), position, AvoidanceStrategy())
}

class KnightFactory(private val idManager: IdManager) : MobFactory {
    override fun getAggressiveMob(position: Position): Mob = Knight(idManager.getNextId(), position, AggressiveStrategy())

    override fun getPassiveMob(position: Position): Mob = Knight(idManager.getNextId(), position, PassiveStrategy())

    override fun getAvoidingMob(position: Position): Mob = Knight(idManager.getNextId(), position, AvoidanceStrategy())
}
