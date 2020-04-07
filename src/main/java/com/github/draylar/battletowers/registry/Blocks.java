package com.github.draylar.battletowers.registry;

import com.github.draylar.battletowers.block.BossLockBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {

    public static Block BOSS_LOCK = register("boss_lock", new BossLockBlock());

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier("battle-towers", name), block);
    }

    public static void init() {

    }

    private Blocks() {
        // NO-OP
    }
}
