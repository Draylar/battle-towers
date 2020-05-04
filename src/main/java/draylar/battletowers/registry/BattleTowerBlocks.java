package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.block.BossLockBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class BattleTowerBlocks {

    public static final Block BOSS_LOCK = register("boss_lock", new BossLockBlock());

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, BattleTowers.id(name), block);
    }

    public static void init() {

    }

    private BattleTowerBlocks() {
        // NO-OP
    }
}
