package roguelike.state.game.world.objects.units

import roguelike.state.game.world.objects.GameItem

/**
 * Инвентарь
 */
data class Inventory(
    val items: MutableList<ItemData> = mutableListOf(),
) {
    data class ItemData(
        val item: GameItem,
        var state: State
    ) {
        enum class State {
            EQUIPPED,
            UNEQUIPPED
        }
    }
}

// Вспомогательные функции для работы с инвентарём

fun Inventory.toggle(item: Int): Boolean {
    if (item !in items.indices) {
        return false
    }
    items[item].state = if (items[item].state == Inventory.ItemData.State.EQUIPPED) {
        Inventory.ItemData.State.UNEQUIPPED
    } else {
        Inventory.ItemData.State.EQUIPPED
    }
    return true
}

fun Inventory.foundItem(item: GameItem): Boolean =
    if (items.size < 5) {
        items.add(Inventory.ItemData(item, Inventory.ItemData.State.UNEQUIPPED))
        true
    } else {
        false
    }