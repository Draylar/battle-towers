package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.block.BossLockBlock;
import draylar.battletowers.block.ContentDeployerBlock;
import draylar.battletowers.block.LadderDeployerBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class BattleTowerBlocks {

    public static final Block BOSS_LOCK = register("boss_lock", new BossLockBlock(), new Item.Settings().group(BattleTowers.GROUP));
    public static final Block LADDER_DEPLOYER = register("ladder_deployer", new LadderDeployerBlock(), new Item.Settings());
    public static final Block CONTENT_DEPLOYER = register("content_deployer", new ContentDeployerBlock(), new Item.Settings());

    private BattleTowerBlocks() {
        // NO-OP
    }

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, BattleTowers.id(name), block);
    }

    private static Block register(String name, Block block, Item.Settings settings) {
        Block registeredBlock = Registry.register(Registry.BLOCK, BattleTowers.id(name), block);
        Registry.register(Registry.ITEM, BattleTowers.id(name), new BlockItem(registeredBlock, settings));
        return registeredBlock;
    }

    public static void init() {
        // NO-OP
    }
}
