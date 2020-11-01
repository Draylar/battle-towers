package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.entity.*;
import draylar.battletowers.entity.block.ContentDeployerBlockEntity;
import draylar.battletowers.entity.block.LadderDeployerBlockEntity;
import draylar.battletowers.entity.themed.ThemedCreeperEntity;
import draylar.battletowers.entity.themed.ThemedSkeletonEntity;
import draylar.battletowers.entity.themed.ThemedSpiderEntity;
import draylar.battletowers.entity.themed.ThemedZombieEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.registry.Registry;

public class BattleTowerEntities {

    public static final EntityType<TowerGuardianEntity> TOWER_GUARD = register("tower_guard", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TowerGuardianEntity::new).dimensions(EntityDimensions.fixed(3, 5)).trackable(64, 4).build());

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
        FabricDefaultAttributeRegistry.register(BattleTowerEntities.TOWER_GUARD, TowerGuardianEntity.createGuardianAttributes());
    }

    private BattleTowerEntities() {
        // NO-OP
    }
}
