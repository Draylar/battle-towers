package com.github.draylar.battleTowers.common;

import com.github.draylar.battleTowers.common.items.BossKeyItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.registry.Registry;

public class Items
{
    public static BossKeyItem bossKeyItem;
    public static BlockItem bossLockItem;

    public static void registerItems()
    {
        bossLockItem = Registry.register(Registry.ITEM, "battle-towers:boss_lock", new BlockItem(Blocks.bossLockBlock, new Item.Settings().itemGroup(ItemGroup.MISC)));
        bossKeyItem = Registry.register(Registry.ITEM, "battle-towers:boss_key", new BossKeyItem(new Item.Settings().itemGroup(ItemGroup.MISC)));
    }
}
