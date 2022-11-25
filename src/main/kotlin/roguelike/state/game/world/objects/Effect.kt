package roguelike.state.game.world.objects

/**
 * Продолжительный эффект
 */
fun interface Effect {
    /**
     * Обновляет внутреннее состояние
     *
     * @return правда ли, что эффект не закончился
     */
    fun update(tick: Int): Boolean
}