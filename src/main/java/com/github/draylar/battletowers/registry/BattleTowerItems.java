package com.github.draylar.battletowers.registry;

import com.github.draylar.battletowers.BattleTowers;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BattleTowerItems {

    public static final Item BOSS_LOCK = register("boss_lock", new BlockItem(BattleTowerBlocks.BOSS_LOCK, new Item.Settings().group(ItemGroup.MISC)));
    public static final Item KEY = register("boss_key", new Item(new net.minecraft.item.Item.Settings().group(ItemGroup.MISC)));

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, BattleTowers.id(name), item);
    }

    public static void init() {

    }

    private BattleTowerItems() {
        // NO-OP
    }
}
