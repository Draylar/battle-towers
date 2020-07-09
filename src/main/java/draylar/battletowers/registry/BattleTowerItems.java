package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.item.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

public class BattleTowerItems {

    public static final Item BOSS_KEY = register("boss_key", new Item(new net.minecraft.item.Item.Settings().group(BattleTowers.GROUP)));

    public static final Item KEY_HELMET = register("key_helmet", new KeyArmorItem(ArmorMaterials.IRON, EquipmentSlot.HEAD, new Item.Settings().group(BattleTowers.GROUP)));
    public static final Item KEY_CHESTPLATE = register("key_chestplate", new KeyArmorItem(ArmorMaterials.IRON, EquipmentSlot.CHEST, new Item.Settings().group(BattleTowers.GROUP)));
    public static final Item KEY_LEGGINGS = register("key_leggings", new KeyArmorItem(ArmorMaterials.IRON, EquipmentSlot.LEGS, new Item.Settings().group(BattleTowers.GROUP)));
    public static final Item KEY_BOOTS = register("key_boots", new KeyArmorItem(ArmorMaterials.IRON, EquipmentSlot.FEET, new Item.Settings().group(BattleTowers.GROUP)));

    public static final SwordItem KEY_SWORD = register("key_sword", new KeySwordItem(new Item.Settings().group(BattleTowers.GROUP)));
    public static final AxeItem KEY_AXE = register("key_axe", new KeyAxeItem(new Item.Settings().group(BattleTowers.GROUP)));
    public static final HoeItem KEY_HOE = register("key_hoe", new KeyHoeItem(new Item.Settings().group(BattleTowers.GROUP)));
    public static final PickaxeItem KEY_PICKAXE = register("key_pickaxe", new KeyPickaxeItem(new Item.Settings().group(BattleTowers.GROUP)));
    public static final ShovelItem KEY_SPADE = register("key_shovel", new KeyShovelItem(new Item.Settings().group(BattleTowers.GROUP)));

    public static final SpawnEggItem TOWER_GUARDIAN_SPAWN_EGG = register(
            "tower_guardian_spawn_egg",
            new SpawnEggItem(
                    BattleTowerEntities.TOWER_GUARD,
                    0xbfbfbf,
                    0xfcba03,
                    new Item.Settings().group(BattleTowers.GROUP)
            )
    );

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, BattleTowers.id(name), item);
    }

    public static void init() {

    }

    private BattleTowerItems() {
        // NO-OP
    }
}
