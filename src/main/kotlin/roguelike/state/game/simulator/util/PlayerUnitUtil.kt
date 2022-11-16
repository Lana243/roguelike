package roguelike.state.game.simulator.util

import roguelike.state.game.world.objects.units.Inventory

fun Inventory.equip(itemId: Int): Inventory? =
    if (itemIds.contains(itemId)) {
        copy(equippedIds = equippedIds + itemId)
    } else {
        null
    }