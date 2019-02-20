package com.github.draylar.battleTowers.common;

import com.github.draylar.battleTowers.common.blocks.boss_lock.BossLockBlock;
import net.minecraft.util.registry.Registry;

public class Blocks
{
    public static BossLockBlock bossLockBlock;

    public static void registerBlocks()
    {
        bossLockBlock = Registry.register(Registry.BLOCK, "battle-towers:boss_lock", new BossLockBlock());
    }
}
