package com.github.draylar.battleTowers.common;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {
    public static Item BOSS_LOCK = register("boss_lock", new BlockItem(Blocks.BOSS_LOCK, new Item.Settings().group(ItemGroup.MISC)));
    public static Item KEY = register("boss_key", new Item(new net.minecraft.item.Item.Settings().group(ItemGroup.MISC)));

    public static void init() {

    }

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("battle-towers", name), item);
    }
}
