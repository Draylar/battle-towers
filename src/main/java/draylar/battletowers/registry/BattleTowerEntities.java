package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.entity.ContentDeployerBlockEntity;
import draylar.battletowers.entity.LadderDeployerBlockEntity;
import draylar.battletowers.entity.TowerGuardEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class BattleTowerEntities {

    public static final EntityType<TowerGuardEntity> TOWER_GUARD = register("tower_guard", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TowerGuardEntity::new).size(EntityDimensions.fixed(3, 6)).size(EntityDimensions.changing(2, 5)).trackable(64, 4).build());

    public static final BlockEntityType<LadderDeployerBlockEntity> LADDER_DEPLOYER = register(
            "ladder_deployer",
            BlockEntityType.Builder.create(LadderDeployerBlockEntity::new, BattleTowerBlocks.LADDER_DEPLOYER).build(null)
    );

    public static final BlockEntityType<ContentDeployerBlockEntity> CONTENT_DEPLOYER = register(
            "content_deployer",
            BlockEntityType.Builder.create(ContentDeployerBlockEntity::new, BattleTowerBlocks.CONTENT_DEPLOYER).build(null)
    );

    private static <T extends LivingEntity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, BattleTowers.id(name), type);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, BattleTowers.id(name), type);
    }

    public static void init() {

    }

    private BattleTowerEntities() {
        // NO-OP
    }
}
