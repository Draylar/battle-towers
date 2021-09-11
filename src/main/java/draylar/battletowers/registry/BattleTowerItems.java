package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.item.*;
import draylar.battletowers.material.KeyArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;

public class BattleTowerItems {

    public static final Item BOSS_KEY = register("boss_key", new Item(new net.minecraft.item.Item.Settings().group(BattleTowers.GROUP)));
    public static final Item KEY_HELMET = register("key_helmet", new KeyArmorItem(KeyArmorMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().group(BattleTowers.GROUP)));
    public static final Item KEY_CHESTPLATE = register("key_chestplate", new KeyArmorItem(KeyArmorMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Settings().group(BattleTowers.GROUP)));
    public static final Item KEY_LEGGINGS = register("key_leggings", new KeyArmorItem(KeyArmorMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Settings().group(BattleTowers.GROUP)));
    public static final Item KEY_BOOTS = register("key_boots", new KeyArmorItem(KeyArmorMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Settings().group(BattleTowers.GROUP)));

    public static final SpawnEggItem TOWER_GUARDIAN_SPAWN_EGG = register("tower_guardian_spawn_egg", new SpawnEggItem(BattleTowerEntities.TOWER_GUARD, 0xbfbfbf, 0xfcba03, new Item.Settings().group(BattleTowers.GROUP)));

    private BattleTowerItems() {
        // NO-OP
    }

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, BattleTowers.id(name), item);
    }

    public static void init() {
        // tool set
        register("key_sword", new KeySwordItem(new Item.Settings().group(BattleTowers.GROUP)));
        register("key_axe", new KeyAxeItem(new Item.Settings().group(BattleTowers.GROUP)));
        register("key_hoe", new KeyHoeItem(new Item.Settings().group(BattleTowers.GROUP)));
        register("key_pickaxe", new KeyPickaxeItem(new Item.Settings().group(BattleTowers.GROUP)));
        register("key_shovel", new KeyShovelItem(new Item.Settings().group(BattleTowers.GROUP)));

        register("foreign_key", new ForeignKeyItem(new Item.Settings().group(BattleTowers.GROUP)));
    }
}
