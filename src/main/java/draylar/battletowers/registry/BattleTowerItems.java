package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

public class BattleTowerItems {

    public static final Item BOSS_KEY = register("boss_key", new Item(new net.minecraft.item.Item.Settings().group(BattleTowers.GROUP)));
    public static final Item TOWER_GUARDIAN_SKULL = register("tower_guardian_skull", new ArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new Item.Settings().group(BattleTowers.GROUP)));

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, BattleTowers.id(name), item);
    }

    public static void init() {

    }

    private BattleTowerItems() {
        // NO-OP
    }
}
