package com.github.draylar.battleTowers.common;

import com.github.draylar.battleTowers.blocks.boss_lock.BossLockBlock;
import com.github.draylar.battleTowers.blocks.spawner.DungeonSpawnerBlock;
import net.minecraft.util.registry.Registry;

public class Blocks
{
    public static BossLockBlock bossLockBlock;
    public static DungeonSpawnerBlock dungeonSpawnerBlock;

    public static void registerBlocks()
    {
        bossLockBlock = Registry.register(Registry.BLOCK, "battle-towers:boss_lock", new BossLockBlock());
        dungeonSpawnerBlock = Registry.register(Registry.BLOCK, "battle-towers:dungeon_spawner", new DungeonSpawnerBlock());
    }
}
