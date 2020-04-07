package com.github.draylar.battleTowers.common;

import com.github.draylar.battleTowers.common.blocks.BossLockBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {
    static Block BOSS_LOCK = register("boss_lock", new BossLockBlock());

    public static void init() {

    }

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier("battle-towers", name), block);
    }
}
