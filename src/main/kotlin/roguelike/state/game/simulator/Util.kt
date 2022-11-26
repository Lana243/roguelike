package roguelike.state.game.simulator

import roguelike.state.game.world.objects.Sword
import roguelike.state.game.world.objects.units.Inventory
import roguelike.state.game.world.objects.units.PlayerUnit

fun PlayerUnit.isSwordEquipped(): Boolean =
    inventory.items.any { it.item is Sword && it.state == Inventory.ItemData.State.EQUIPED }